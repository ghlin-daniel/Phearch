package com.brickgit.imagesearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class ImageGridViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.root) View rootView;
    @BindView(R.id.image) SimpleDraweeView imageView;

    public ImageGridViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(ImageInfoResponse imageInfo) {
        rootView.setTag(imageInfo);
        imageView.setImageURI(imageInfo.getPreviewURL());
    }
}
