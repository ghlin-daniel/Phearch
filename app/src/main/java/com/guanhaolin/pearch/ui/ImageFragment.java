package com.guanhaolin.pearch.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.guanhaolin.pearch.R;
import com.guanhaolin.pearch.adapter.ImageAdapter;
import com.guanhaolin.pearch.adapter.SpaceItemDecoration;
import com.guanhaolin.pearch.model.MediaViewModel;

public class ImageFragment extends Fragment {

  private MediaViewModel mViewModel;

  ProgressBar progressBar;

  RecyclerView listView;

  private StaggeredGridLayoutManager layoutManager;

  private final ImageAdapter.OnImageCellClickListener onImageCellClickListener =
      imageInfo -> {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageInfo.pageURL));
        startActivity(intent);
      };

  private final RecyclerView.OnScrollListener onScrollListener =
      new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);

          int totalItemCount = layoutManager.getItemCount();
          int visibleItemCount = layoutManager.getChildCount();
          int[] firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(null);
          int firstVisibleItemPosition =
              firstVisibleItemPositions[firstVisibleItemPositions.length - 1];

          if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
            mViewModel.loadMorePhotos();
          }
        }
      };

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image, container, false);

    listView = view.findViewById(R.id.image_list_view);
    progressBar = view.findViewById(R.id.image_progress_bar);

    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
    listView.setLayoutManager(layoutManager);
    ImageAdapter adapter = new ImageAdapter();
    adapter.setOnImageCellClickListener(onImageCellClickListener);
    listView.setAdapter(adapter);
    listView.addItemDecoration(new SpaceItemDecoration());
    listView.addOnScrollListener(onScrollListener);

    mViewModel = new ViewModelProvider(getActivity()).get(MediaViewModel.class);
    mViewModel
        .getQuery()
        .observe(
            getViewLifecycleOwner(),
            query -> {
              showProgressBar(true);
              adapter.clear();
            });
    mViewModel
        .getPhotos()
        .observe(
            getViewLifecycleOwner(),
            imageInfoResponses -> {
              showProgressBar(false);
              adapter.update(imageInfoResponses);
            });

    return view;
  }

  private void showProgressBar(boolean show) {
    progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }
}