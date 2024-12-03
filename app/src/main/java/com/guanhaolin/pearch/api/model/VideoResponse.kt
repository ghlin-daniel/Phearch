package com.guanhaolin.pearch.api.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("videos")
    val videos: VideosResponse,
    @SerializedName("views")
    val views: Int,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user")
    val user: String,
    @SerializedName("userImageURL")
    val userImageURL: String
)