package com.guanhaolin.pearch.api

import app.cash.turbine.test
import com.google.gson.Gson
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.api.model.SearchResultResponse
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.api.model.imageResponseBody
import com.guanhaolin.pearch.api.model.videoResponseBody
import com.guanhaolin.pearch.data.MediaRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MediaRepositoryTest {
    private lateinit var pixabayService: PixabayService

    private lateinit var responseImage: ImageResponse
    private lateinit var responseVideo: VideoResponse

    @Before
    fun setUp() {
        pixabayService = mockk()

        val gson = Gson().newBuilder().create()
        responseImage = gson.fromJson(imageResponseBody, ImageResponse::class.java)
        responseVideo = gson.fromJson(videoResponseBody, VideoResponse::class.java)
    }

    @Test
    fun `searchImages should return search result when API call is successful`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val mediaRepository = MediaRepositoryImpl(pixabayService, dispatcher)

        coEvery { pixabayService.searchImages(any(), any(), any()) } returns SearchResultResponse(
            0,
            0,
            listOf(responseImage)
        )

        mediaRepository.searchImages("android", 1).test {
            val result = awaitItem()
            assertTrue(result.isNotEmpty())
            assertEquals(responseImage.id, result.first().id)
            awaitComplete()
            coVerify { pixabayService.searchImages(any(), any(), any()) }
        }
    }

    @Test
    fun `searchVideos should return search result when API call is successful`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val mediaRepository = MediaRepositoryImpl(pixabayService, dispatcher)

        coEvery { pixabayService.searchVideos(any(), any(), any()) } returns SearchResultResponse(
            0,
            0,
            listOf(responseVideo)
        )

        mediaRepository.searchVideos("android", 1).test {
            val result = awaitItem()
            assertTrue(result.isNotEmpty())
            assertEquals(responseVideo.id, result.first().id)
            awaitComplete()
            coVerify { pixabayService.searchVideos(any(), any(), any()) }
        }
    }
}
