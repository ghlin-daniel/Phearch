package com.brickgit.imagesearch.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Lin on 6/30/16.
 */
public class ImageInfoResponse {

    private int id;
    private String pageURL;
    private String type;

    private String tags;

    private String previewURL;
    private int previewWidth;
    private int previewHeight;

    private String webformatURL;
    private int webformatWidth;
    private int webformatHeight;

    private int imageWidth;
    private int imageHeight;

    private Count views;
    private Count downloads;
    private Count favorites;
    private Count likes;
    private Count comments;

    @SerializedName("user_id")
    private int userId;
    private String user;
    private String userImageURL;

    public ImageInfoResponse() {}

    public int getId() {
        return id;
    }

    public String getPageURL() {
        return pageURL;
    }

    public String getType() {
        return type;
    }

    public String getTags() {
        return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public int getWebformatWidth() {
        return webformatWidth;
    }

    public int getWebformatHeight() {
        return webformatHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Count getViews() {
        return views;
    }

    public Count getDownloads() {
        return downloads;
    }

    public Count getFavorites() {
        return favorites;
    }

    public Count getLikes() {
        return likes;
    }

    public Count getComments() {
        return comments;
    }

    public int getUserId() {
        return userId;
    }

    public String getUser() {
        return user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }
}
