package com.brickgit.imagesearch.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.adapter.ImageAdapter;
import com.brickgit.imagesearch.adapter.SpaceItemDecoration;
import com.brickgit.imagesearch.api.QueryApi;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.brickgit.imagesearch.model.QueryRequest;
import com.brickgit.imagesearch.model.QueryResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private enum Mode {
        ListMode, GridMode
    }

    private Mode mode = Mode.ListMode;

    private boolean noMoreImage = false;
    private boolean loading = false;
    private QueryRequest request;

    private SearchView searchView;

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.list_view) RecyclerView listView;
    private StaggeredGridLayoutManager layoutManager;
    private ImageAdapter adapter;

    private final ImageAdapter.OnImageCellClickListener onImageCellClickListener =
            new ImageAdapter.OnImageCellClickListener() {
                @Override
                public void onImageCellClicked(ImageInfoResponse imageInfo) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageInfo.getPageURL()));
                    startActivity(intent);
                }
            };

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            query = query.trim();
            if (query.isEmpty()) {
                searchView.setQuery("", false);
                return true;
            }

            showProgressBar(true);
            adapter.clear();

            request = QueryRequest.getNewRequest(query);
            searchImages();

            searchView.setQuery("", false);
            searchView.setIconified(true);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (loading || noMoreImage) return;

            int totalItemCount = layoutManager.getItemCount();
            int visibleItemCount = layoutManager.getChildCount();
            int[] firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(null);
            int firstVisibleItemPosition = firstVisibleItemPositions[firstVisibleItemPositions.length - 1];

            if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                request.nextPage();
                searchImages();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        adapter = new ImageAdapter(listView);
        adapter.setOnImageCellClickListener(onImageCellClickListener);
        listView.setAdapter(adapter);
        listView.addItemDecoration(new SpaceItemDecoration());
        listView.addOnScrollListener(onScrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem switchLayout = menu.findItem(R.id.switch_layout);
        switchLayout.setIcon(mode == Mode.GridMode ? R.drawable.ic_action_grid : R.drawable.ic_action_list);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.switch_layout:
                mode = (mode == Mode.GridMode ? Mode.ListMode : Mode.GridMode);
                layoutManager.setSpanCount(mode == Mode.GridMode ? 3 : 1);

                invalidateOptionsMenu();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void searchImages() {

        if (request == null) return;

        loading = true;

        QueryApi.searchImage(this, request, new QueryApi.ApiCallback() {
            @Override
            public void onResponse(QueryRequest request, QueryResponse response) {
                if (!request.equals(MainActivity.this.request)) return;

                showProgressBar(false);

                if (response.getHits().size() != 0) {
                    adapter.add(response.getHits());
                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_result), Toast.LENGTH_LONG).show();
                }

                loading = false;
            }

            @Override
            public void onErrorResponse(QueryRequest request, String error) {
                if (!request.equals(MainActivity.this.request)) return;

                showProgressBar(false);

                if (error.contains("out of valid range")) {
                    noMoreImage = true;
                    Toast.makeText(MainActivity.this, getString(R.string.no_more_image), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.failed_to_search_image), Toast.LENGTH_LONG).show();
                }

                loading = false;
            }
        });
    }

    private void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
