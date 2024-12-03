package com.guanhaolin.pearch.api.model

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("large")
    val large: VideoDetailResponse,
    @SerializedName("medium")
    val medium: VideoDetailResponse,
    @SerializedName("small")
    val small: VideoDetailResponse,
    @SerializedName("tiny")
    val tiny: VideoDetailResponse
)
