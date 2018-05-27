package com.brickgit.imagesearch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Lin on 6/30/16.
 */
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
	public Count favorites;
	public Count likes;
	public Count comments;

    @SerializedName("user_id")
    public int userId;
	public String user;
	public String userImageURL;

    public ImageInfoResponse() {}
}
