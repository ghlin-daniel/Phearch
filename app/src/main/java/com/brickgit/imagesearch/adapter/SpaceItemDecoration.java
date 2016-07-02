package com.brickgit.imagesearch.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space = 16;

    public SpaceItemDecoration() {}

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = space;
    }
}
