package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.VideoInfoResponse;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public interface OnVideoCellClickListener {
		void onVideoCellClicked(VideoInfoResponse videoInfo);
	}

	private List<VideoInfoResponse> listVideoInfo = new ArrayList<>();

	private VideoAdapter.OnVideoCellClickListener onVideoCellClickListener;

	private final View.OnClickListener onClickListener = new View.OnClickListener() {
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

	public void setOnVideoCellClickListener(VideoAdapter.OnVideoCellClickListener onVideoCellClickListener) {
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

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater
				.from(parent.getContext())
				.inflate(R.layout.video_view_holder, null);
		view.setOnClickListener(onClickListener);
		return new VideoViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		VideoInfoResponse videoInfo = listVideoInfo.get(position);
		VideoViewHolder viewHolder = (VideoViewHolder) holder;
		viewHolder.bind(videoInfo);
	}

	@Override
	public int getItemCount() {
		return listVideoInfo.size();
	}
}
