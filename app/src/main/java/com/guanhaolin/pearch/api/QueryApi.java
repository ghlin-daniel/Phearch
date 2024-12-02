package com.guanhaolin.pearch.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.guanhaolin.pearch.BuildConfig;
import com.guanhaolin.pearch.model.ImageInfoResponse;
import com.guanhaolin.pearch.model.QueryResponse;
import com.guanhaolin.pearch.model.ResponseParser;
import com.guanhaolin.pearch.model.VideoInfoResponse;
import com.guanhaolin.pearch.util.NetworkUtil;

/** Created by Daniel Lin on 7/1/16. */
public class QueryApi {

  public interface ApiCallback<T> {
    void onResponse(String query, QueryResponse<T> response);

    void onErrorResponse(String query, String error);
  }

  public static void searchImage(
      Context context,
      final String query,
      final int page,
      final ApiCallback<ImageInfoResponse> apiCallback) {

    final String apiKey = BuildConfig.PHEARCH_PIXABAY_API_KEY;

    String formattedQuery = query.replace(" ", "+");
    String url =
        "https://pixabay.com/api/?key="
            + apiKey
            + "&q="
            + formattedQuery
            + "&image_type=all"
            + "&page="
            + page;

    StringRequest stringRequest =
        new StringRequest(
            Request.Method.GET,
            url,
            response -> {
              QueryResponse<ImageInfoResponse> queryResponse =
                  ResponseParser.getInstance().parseImageResponse(response);
              if (apiCallback != null) {
                apiCallback.onResponse(query, queryResponse);
              }
            },
            error -> {
              if (apiCallback != null) {
                apiCallback.onErrorResponse(query, new String(error.networkResponse.data));
              }
            });

    NetworkUtil.getInstance(context).addToRequestQueue(stringRequest);
  }

  public static void searchVideo(
      Context context,
      final String query,
      final int page,
      final ApiCallback<VideoInfoResponse> apiCallback) {

    final String apiKey = BuildConfig.PHEARCH_PIXABAY_API_KEY;

    String formattedQuery = query.replace(" ", "+");
    String url =
        "https://pixabay.com/api/videos/?key="
            + apiKey
            + "&q="
            + formattedQuery
            + "&video_type=all"
            + "&page="
            + page;

    StringRequest stringRequest =
        new StringRequest(
            Request.Method.GET,
            url,
            response -> {
              QueryResponse<VideoInfoResponse> queryResponse =
                  ResponseParser.getInstance().parseVideoResponse(response);
              if (apiCallback != null) {
                apiCallback.onResponse(query, queryResponse);
              }
            },
            error -> {
              if (apiCallback != null) {
                apiCallback.onErrorResponse(query, new String(error.networkResponse.data));
              }
            });

    NetworkUtil.getInstance(context).addToRequestQueue(stringRequest);
  }
}
