package com.brickgit.imagesearch.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.brickgit.imagesearch.BuildConfig;
import com.brickgit.imagesearch.model.QueryRequest;
import com.brickgit.imagesearch.model.QueryResponse;
import com.brickgit.imagesearch.util.NetworkUtil;
import com.google.gson.Gson;

/**
 * Created by Daniel Lin on 7/1/16.
 */
public class QueryApi {

    public interface ApiCallback {
        void onResponse(QueryRequest request, QueryResponse response);
        void onErrorResponse(QueryRequest request, String error);
    }

    public static void searchImage(
    		Context context, final QueryRequest request, final ApiCallback apiCallback) {

        final String apiKey = BuildConfig.PixabayApiKey;

        String formattedQuery = request.getQuery().replace(" ", "+");
        String url =
		        "https://pixabay.com/api/?key=" + apiKey +
		        "&q=" + formattedQuery +
		        "&image_type=all" +
		        "&page=" + request.getPage();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        QueryResponse queryResponse = gson.fromJson(response, QueryResponse.class);

                        if (apiCallback != null) {
                            apiCallback.onResponse(request, queryResponse);
                        }
                    }
                },
		        new Response.ErrorListener() {
        	        @Override
	                public void onErrorResponse(VolleyError error) {
        	        	if (apiCallback != null) {
        	        		apiCallback.onErrorResponse(
        	        				request, new String(error.networkResponse.data));
        	        	}
        	        }
                });

        NetworkUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
