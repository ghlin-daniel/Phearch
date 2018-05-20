package com.brickgit.imagesearch.util;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class NetworkUtil {

    private static NetworkUtil instance;

    private RequestQueue requestQueue;

    public static synchronized NetworkUtil getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtil(context);
        }
        return instance;
    }

    private NetworkUtil(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }
}
