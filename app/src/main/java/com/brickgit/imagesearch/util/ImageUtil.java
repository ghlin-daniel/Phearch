package com.brickgit.imagesearch.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class ImageUtil {
    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context.getApplicationContext()).load(imageUrl)
                .centerCrop().into(imageView);
    }
}
