package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.VideoInfoResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.root) View rootView;
	@BindView(R.id.preview) SimpleDraweeView preview;
	@BindView(R.id.likes) TextView likes;
	@BindView(R.id.favorites) TextView favorites;
	@BindView(R.id.views) TextView views;
	@BindView(R.id.downloads) TextView downloads;
	@BindView(R.id.comments) TextView comments;

	public VideoViewHolder(View view) {
		super(view);
		ButterKnife.bind(this, view);
	}

	public void bind(VideoInfoResponse videoInfo) {
		rootView.setTag(videoInfo);
		preview.setImageURI(String.format("https://i.vimeocdn.com/video/%s_640x360.jpg", videoInfo.picture_id));
		likes.setText(videoInfo.likes.getCount());
		favorites.setText(videoInfo.favorites.getCount());
		views.setText(videoInfo.views.getCount());
		downloads.setText(videoInfo.downloads.getCount());
		comments.setText(videoInfo.comments.getCount());
	}
}
