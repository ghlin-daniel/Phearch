package com.brickgit.imagesearch.model;

import com.google.gson.annotations.SerializedName;

public class VideoInfoResponse {
	public int id;
	public String pageURL;
	public String type;
	public String tags;
	public int duration;
	public String picture_id;

	public Count views;
	public Count downloads;
	public Count favorites;
	public Count likes;
	public Count comments;

	@SerializedName("user_id")
	public int userId;
	public String user;
	public String userImageURL;

	public VideosInfoData videos;
}
