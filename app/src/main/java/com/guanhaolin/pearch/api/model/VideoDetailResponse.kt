package com.guanhaolin.pearch.api.model

import com.google.gson.annotations.SerializedName

data class VideoDetailResponse(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("thumbnail")
    val thumbnail: String
)
