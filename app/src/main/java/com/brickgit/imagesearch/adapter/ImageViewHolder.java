package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel Lin on 7/2/16.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.root) View rootView;
    @BindView(R.id.image) SimpleDraweeView imageView;
    @BindView(R.id.likes) TextView likes;
    @BindView(R.id.favorites) TextView favorites;
    @BindView(R.id.views) TextView views;
    @BindView(R.id.downloads) TextView downloads;
    @BindView(R.id.comments) TextView comments;

    public ImageViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(ImageInfoResponse imageInfo) {
        rootView.setTag(imageInfo);
	    imageView.setImageURI(imageInfo.webformatURL);
        likes.setText(imageInfo.likes.getCount());
        favorites.setText(imageInfo.favorites.getCount());
        views.setText(imageInfo.views.getCount());
        downloads.setText(imageInfo.downloads.getCount());
        comments.setText(imageInfo.comments.getCount());
    }
}
