package com.guanhaolin.pearch.model;

import com.google.gson.annotations.SerializedName;

public class VideoInfoResponse {
  public int id;
  public String pageURL;
  public String type;
  public String tags;
  public int duration;
  public Count views;
  public Count downloads;
  public Count likes;
  public Count comments;

  @SerializedName("user_id")
  public int userId;

  public String user;
  public String userImageURL;

  public VideosInfoData videos;
}
