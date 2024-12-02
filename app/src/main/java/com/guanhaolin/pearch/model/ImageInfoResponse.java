package com.guanhaolin.pearch.model;

import com.google.gson.annotations.SerializedName;

public class ImageInfoResponse {

  public int id;
  public String pageURL;
  public String type;
  public String tags;

  public String previewURL;
  public int previewWidth;
  public int previewHeight;

  public String webformatURL;
  public int webformatWidth;
  public int webformatHeight;

  public int imageWidth;
  public int imageHeight;

  public Count views;
  public Count downloads;
  public Count likes;
  public Count comments;

  @SerializedName("user_id")
  public int userId;

  public String user;
  public String userImageURL;

  public ImageInfoResponse() {}
}
