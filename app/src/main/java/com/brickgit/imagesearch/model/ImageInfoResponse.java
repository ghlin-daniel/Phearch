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

    private int views;
    private int downloads;
    private int favorites;
    private int likes;
    private int comments;

    @SerializedName("user_id")
    private int userId;
    private String user;
    private String userImageURL;

    private QueryRequest request;

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

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
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

    public QueryRequest getRequest() {
        return request;
    }

    public void setRequest(QueryRequest request) {
        this.request = request;
    }
}
