package com.guanhaolin.pearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.guanhaolin.pearch.databinding.ImageViewHolderBinding;
import com.guanhaolin.pearch.model.ImageInfoResponse;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter
    extends androidx.recyclerview.widget.RecyclerView.Adapter<
        androidx.recyclerview.widget.RecyclerView.ViewHolder> {

  public interface OnImageCellClickListener {
    void onImageCellClicked(ImageInfoResponse imageInfo);
  }

  private final List<ImageInfoResponse> listImageInfo = new ArrayList<>();

  private OnImageCellClickListener onImageCellClickListener;

  private final View.OnClickListener onClickListener =
      new View.OnClickListener() {
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
    ImageInfoResponse imageInfo = listImageInfo.get(position);
    ImageViewHolder viewHolder = (ImageViewHolder) holder;
    viewHolder.bind(imageInfo);
  }

  @Override
  public int getItemCount() {
    return listImageInfo.size();
  }
}
