package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.ImageInfoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnImageCellClickListener {
        void onImageCellClicked(ImageInfoResponse imageInfo);
    }

    private static final int VIEW_TYPE_GRID_CELL = 0;
    private static final int VIEW_TYPE_LIST_CELL = 1;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager layoutManager;

    private List<ImageInfoResponse> listImageInfo = new ArrayList<>();

    private OnImageCellClickListener onImageCellClickListener;

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onImageCellClickListener != null) {
                ImageInfoResponse imageInfo = (ImageInfoResponse) view.getTag();
                if (imageInfo != null) {
                    onImageCellClickListener.onImageCellClicked(imageInfo);
                }
            }
        }
    };

    public ImageAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        layoutManager = (StaggeredGridLayoutManager) this.recyclerView.getLayoutManager();
    }

    public void setOnImageCellClickListener(OnImageCellClickListener onImageCellClickListener) {
        this.onImageCellClickListener = onImageCellClickListener;
    }

    public void add(List<ImageInfoResponse> moreImageInfo) {
        int originalLength = listImageInfo.size();
        listImageInfo.addAll(moreImageInfo);
        notifyItemRangeInserted(originalLength, moreImageInfo.size());
    }

    public void clear() {
        listImageInfo.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (layoutManager.getSpanCount() == 3 ? VIEW_TYPE_GRID_CELL : VIEW_TYPE_LIST_CELL);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_GRID_CELL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_grid_view_holder, null);
            view.setOnClickListener(onClickListener);
            ImageGridViewHolder viewHolder = new ImageGridViewHolder(parent.getContext(), view);
            return viewHolder;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_view_holder, null);
            view.setOnClickListener(onClickListener);
            ImageListVIewHolder viewHolder = new ImageListVIewHolder(parent.getContext(), view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageInfoResponse imageInfo = listImageInfo.get(position);

        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_GRID_CELL) {
            ImageGridViewHolder gridViewHolder = (ImageGridViewHolder) holder;
            gridViewHolder.bind(imageInfo);
        }
        else {
            ImageListVIewHolder gridViewHolder = (ImageListVIewHolder) holder;
            gridViewHolder.bind(imageInfo);
        }
    }

    @Override
    public int getItemCount() {
        return listImageInfo.size();
    }
}
