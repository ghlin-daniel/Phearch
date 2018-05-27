package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
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

    public void setOnImageCellClickListener(OnImageCellClickListener onImageCellClickListener) {
        this.onImageCellClickListener = onImageCellClickListener;
    }

    public void update(List<ImageInfoResponse> newPhotos) {
    	int size = listImageInfo.size();
	    listImageInfo.clear();
	    listImageInfo.addAll(newPhotos);
	    notifyItemRangeInserted(size, newPhotos.size() - size);
    }

    public void clear() {
        listImageInfo.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	    View view = LayoutInflater
			    .from(parent.getContext())
			    .inflate(R.layout.image_view_holder, null);
	    view.setOnClickListener(onClickListener);
	    return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageInfoResponse imageInfo = listImageInfo.get(position);
	    ImageViewHolder viewHolder = (ImageViewHolder) holder;
	    viewHolder.bind(imageInfo);
    }

    @Override
    public int getItemCount() {
        return listImageInfo.size();
    }
}
