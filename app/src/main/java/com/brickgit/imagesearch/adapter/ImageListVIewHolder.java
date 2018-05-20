package com.brickgit.imagesearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.brickgit.imagesearch.util.ImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Daniel Lin on 7/2/16.
 */
public class ImageListVIewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.root) View rootView;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.likes) TextView likes;
    @BindView(R.id.favorites) TextView favorites;
    @BindView(R.id.views) TextView views;
    @BindView(R.id.downloads) TextView downloads;
    @BindView(R.id.comments) TextView comments;

    public ImageListVIewHolder(Context context, View view) {
        super(view);
        ButterKnife.bind(this, view);

        this.context = context;
    }

    public void bind(ImageInfoResponse imageInfo) {

        rootView.setTag(imageInfo);

        ImageUtil.loadImage(context, imageView, imageInfo.getWebformatURL());

        likes.setText(String.valueOf(imageInfo.getLikes()));
        favorites.setText(String.valueOf(imageInfo.getFavorites()));
        views.setText(String.valueOf(imageInfo.getViews()));
        downloads.setText(String.valueOf(imageInfo.getDownloads()));
        comments.setText(String.valueOf(imageInfo.getComments()));
    }
}
