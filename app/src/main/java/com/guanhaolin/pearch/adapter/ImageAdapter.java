package com.guanhaolin.pearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.guanhaolin.pearch.api.model.ImageResponse;
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter
    extends androidx.recyclerview.widget.RecyclerView.Adapter<
        androidx.recyclerview.widget.RecyclerView.ViewHolder> {

  public interface OnImageCellClickListener {
    void onImageCellClicked(ImageResponse image);
  }

  private final List<ImageResponse> images = new ArrayList<>();

  private OnImageCellClickListener onImageCellClickListener;

  private final View.OnClickListener onClickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (onImageCellClickListener != null) {
            ImageResponse image = (ImageResponse) view.getTag();
            if (image != null) {
              onImageCellClickListener.onImageCellClicked(image);
            }
          }
        }
      };

  public void setOnImageCellClickListener(OnImageCellClickListener onImageCellClickListener) {
    this.onImageCellClickListener = onImageCellClickListener;
  }

  public void update(List<ImageResponse> newPhotos) {
    images.clear();
    images.addAll(newPhotos);
    notifyDataSetChanged();
  }

  public void clear() {
    images.clear();
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ImageViewHolderBinding viewbinding =
        ImageViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()));
    viewbinding.root.setOnClickListener(onClickListener);
    return new ImageViewHolder(viewbinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ImageResponse image = images.get(position);
    ImageViewHolder viewHolder = (ImageViewHolder) holder;
    viewHolder.bind(image);
  }

  @Override
  public int getItemCount() {
    return images.size();
  }
}
