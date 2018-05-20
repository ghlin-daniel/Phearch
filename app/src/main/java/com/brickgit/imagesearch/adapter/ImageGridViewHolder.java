package com.brickgit.imagesearch.adapter;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.brickgit.imagesearch.R;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.brickgit.imagesearch.util.ImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class ImageGridViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    @BindView(R.id.root) View rootView;
    @BindView(R.id.image) ImageView imageView;

    public ImageGridViewHolder(Context context, View view) {
        super(view);
        ButterKnife.bind(this, view);

        this.context = context;
    }

    public void bind(ImageInfoResponse imageInfo) {

        rootView.setTag(imageInfo);

        float imageWidth = imageInfo.getWebformatWidth();
        float imageHeight = imageInfo.getWebformatHeight();

        PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.getPercentLayoutInfo().aspectRatio = imageWidth / imageHeight;
        imageView.setLayoutParams(layoutParams);

        ImageUtil.loadImage(context, imageView, imageInfo.getWebformatURL());
    }
}
