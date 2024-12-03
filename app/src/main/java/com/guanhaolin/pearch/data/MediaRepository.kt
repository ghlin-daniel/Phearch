package com.guanhaolin.pearch.data

import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.api.model.VideoResponse
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchImages(query: String, page: Int): Flow<List<ImageResponse>>
    suspend fun searchVideos(query: String, page: Int): Flow<List<VideoResponse>>
}