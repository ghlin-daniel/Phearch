package com.guanhaolin.pearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.guanhaolin.pearch.databinding.VideoViewHolderBinding;
import com.guanhaolin.pearch.model.VideoInfoResponse;
import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public interface OnVideoCellClickListener {
    void onVideoCellClicked(VideoInfoResponse videoInfo);
  }

  private List<VideoInfoResponse> listVideoInfo = new ArrayList<>();

  private VideoAdapter.OnVideoCellClickListener onVideoCellClickListener;

  private final View.OnClickListener onClickListener =
      new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (onVideoCellClickListener != null) {
            VideoInfoResponse videoInfo = (VideoInfoResponse) view.getTag();
            if (videoInfo != null) {
              onVideoCellClickListener.onVideoCellClicked(videoInfo);
            }
          }
        }
      };

  public void setOnVideoCellClickListener(
      VideoAdapter.OnVideoCellClickListener onVideoCellClickListener) {
    this.onVideoCellClickListener = onVideoCellClickListener;
  }

  public void update(List<VideoInfoResponse> newVideos) {
    int size = listVideoInfo.size();
    listVideoInfo.clear();
    listVideoInfo.addAll(newVideos);
    notifyItemRangeInserted(size, newVideos.size() - size);
  }

  public void clear() {
    listVideoInfo.clear();
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    VideoViewHolderBinding viewBinding =
        VideoViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()));
    viewBinding.root.setOnClickListener(onClickListener);
    return new VideoViewHolder(viewBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    VideoInfoResponse videoInfo = listVideoInfo.get(position);
    VideoViewHolder viewHolder = (VideoViewHolder) holder;
    viewHolder.bind(videoInfo);
  }

  @Override
  public int getItemCount() {
    return listVideoInfo.size();
  }
}
