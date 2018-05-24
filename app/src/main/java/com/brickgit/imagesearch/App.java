package com.brickgit.imagesearch;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
	    Fresco.initialize(this);
    }
}
