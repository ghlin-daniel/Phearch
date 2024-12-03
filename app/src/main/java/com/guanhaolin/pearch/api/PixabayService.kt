package com.guanhaolin.pearch.api

import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.api.model.SearchResultResponse
import com.guanhaolin.pearch.api.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("api")
    suspend fun searchImages(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ): SearchResultResponse<ImageResponse>

    @GET("api/videos")
    suspend fun searchVideos(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ): SearchResultResponse<VideoResponse>
}
