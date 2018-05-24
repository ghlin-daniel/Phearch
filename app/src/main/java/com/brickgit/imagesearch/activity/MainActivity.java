package com.brickgit.imagesearch.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.adapter.ImageAdapter;
import com.brickgit.imagesearch.adapter.SpaceItemDecoration;
import com.brickgit.imagesearch.model.MediaViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private enum Mode {
        ListMode, GridMode
    }

    private Mode mode = Mode.ListMode;
    private MediaViewModel mViewModel;
    private SearchView searchView;

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.list_view) RecyclerView listView;
    private StaggeredGridLayoutManager layoutManager;
    private ImageAdapter adapter;

    private final ImageAdapter.OnImageCellClickListener onImageCellClickListener = imageInfo -> {
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageInfo.getPageURL()));
    	startActivity(intent);
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

	        mViewModel.queryPhotos(query);

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

            int totalItemCount = layoutManager.getItemCount();
            int visibleItemCount = layoutManager.getChildCount();
            int[] firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(null);
            int firstVisibleItemPosition = firstVisibleItemPositions[firstVisibleItemPositions.length - 1];

            if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
	            mViewModel.loadMorePhotos();
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

	    mViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
	    mViewModel.getPhotos().observe(this, imageInfoResponses -> {
	    	showProgressBar(false);
	    	adapter.update(imageInfoResponses);
	    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem switchLayout = menu.findItem(R.id.switch_layout);
        switchLayout.setIcon(mode == Mode.GridMode ? R.drawable.ic_action_grid : R.drawable.ic_action_list);

        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
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

    private void showProgressBar(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
