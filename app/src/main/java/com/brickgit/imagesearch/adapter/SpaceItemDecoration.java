package com.brickgit.imagesearch.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public SpaceItemDecoration() {}

    @Override
    public void getItemOffsets(
    		Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

	    final int span = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
	    if (span == 3) {
		    outRect.right = 3;
		    outRect.left = 3;
	    }
	    outRect.bottom = 3;
	    if (parent.getChildAdapterPosition(view) == 0)
		    outRect.top = 3;
    }
}
