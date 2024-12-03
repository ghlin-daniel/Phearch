package com.guanhaolin.pearch.data

import com.guanhaolin.pearch.api.model.ImageResponse
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchImages(query: String, page: Int): Flow<List<ImageResponse>>
}