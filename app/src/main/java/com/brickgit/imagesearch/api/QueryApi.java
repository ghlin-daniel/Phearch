package com.brickgit.imagesearch.api;

import android.content.Context;
import android.media.Image;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.brickgit.imagesearch.BuildConfig;
import com.brickgit.imagesearch.model.ImageInfoResponse;
import com.brickgit.imagesearch.model.QueryResponse;
import com.brickgit.imagesearch.model.ResponseParser;
import com.brickgit.imagesearch.model.VideoInfoResponse;
import com.brickgit.imagesearch.util.NetworkUtil;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class QueryApi {

    public interface ApiCallback<T> {
        void onResponse(String query, QueryResponse<T> response);
        void onErrorResponse(String query, String error);
    }

    public static void searchImage(
    		Context context, final String query, final int page, final ApiCallback<ImageInfoResponse> apiCallback) {

        final String apiKey = BuildConfig.PixabayApiKey;

        String formattedQuery = query.replace(" ", "+");
        String url =
		        "https://pixabay.com/api/?key=" + apiKey +
		        "&q=" + formattedQuery +
		        "&image_type=all" +
		        "&page=" + page;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        response -> {
			        QueryResponse<ImageInfoResponse> queryResponse =
					        ResponseParser.getInstance().parseImageResponse(response);
			        if (apiCallback != null) {
				        apiCallback.onResponse(query, queryResponse);
			        }
                },
		        error -> {
			        if (apiCallback != null) {
				        apiCallback.onErrorResponse(
						        query, new String(error.networkResponse.data));
			        }
                });

        NetworkUtil.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void searchVideo(
		    Context context, final String query, final int page, final ApiCallback<VideoInfoResponse> apiCallback) {

	    final String apiKey = BuildConfig.PixabayApiKey;

	    String formattedQuery = query.replace(" ", "+");
	    String url =
			    "https://pixabay.com/api/videos/?key=" + apiKey +
					    "&q=" + formattedQuery +
					    "&video_type=all" +
					    "&page=" + page;

	    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
			    response -> {
				    QueryResponse<VideoInfoResponse> queryResponse =
						    ResponseParser.getInstance().parseVideoResponse(response);
				    if (apiCallback != null) {
					    apiCallback.onResponse(query, queryResponse);
				    }
			    },
			    error -> {
				    if (apiCallback != null) {
					    apiCallback.onErrorResponse(
							    query, new String(error.networkResponse.data));
				    }
			    });

	    NetworkUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
