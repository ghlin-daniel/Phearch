package com.guanhaolin.pearch.api.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponse<T>(
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val hits: List<T>
)
