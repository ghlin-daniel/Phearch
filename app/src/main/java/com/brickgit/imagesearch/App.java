package com.brickgit.imagesearch;

import android.app.Application;
import com.brickgit.imagesearch.util.NetworkUtil;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkUtil.getInstance(this);
    }
}
