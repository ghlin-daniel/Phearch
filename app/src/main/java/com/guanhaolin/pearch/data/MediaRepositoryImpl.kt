package com.guanhaolin.pearch.data

import com.guanhaolin.pearch.BuildConfig
import com.guanhaolin.pearch.api.PixabayService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MediaRepositoryImpl(
    private val pixabayService: PixabayService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MediaRepository {
    override suspend fun searchImages(query: String, page: Int) = flow {
        val response = pixabayService.searchImages(
            BuildConfig.PHEARCH_PIXABAY_API_KEY,
            query.replace(' ', '+'),
            page
        )
        emit(response.hits)
    }.flowOn(dispatcher)

    override suspend fun searchVideos(query: String, page: Int) = flow {
        val response = pixabayService.searchVideos(
            BuildConfig.PHEARCH_PIXABAY_API_KEY,
            query.replace(' ', '+'),
            page
        )
        emit(response.hits)
    }.flowOn(dispatcher)
}