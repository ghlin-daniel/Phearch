package com.guanhaolin.pearch.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ResponseParser {

  private static ResponseParser instance;

  private Gson gson;
  private Type imageType = new TypeToken<QueryResponse<ImageInfoResponse>>() {}.getType();
  private Type videoType = new TypeToken<QueryResponse<VideoInfoResponse>>() {}.getType();

  public static synchronized ResponseParser getInstance() {
    if (instance == null) {
      instance = new ResponseParser();
    }
    return instance;
  }

  private ResponseParser() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(Count.class, new CountDeserializer());
    gson = gsonBuilder.create();
  }

  public QueryResponse<ImageInfoResponse> parseImageResponse(String response) {
    return gson.fromJson(response, imageType);
  }

  public QueryResponse<VideoInfoResponse> parseVideoResponse(String response) {
    return gson.fromJson(response, videoType);
  }
}
