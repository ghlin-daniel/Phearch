package com.guanhaolin.pearch.api

import com.google.gson.Gson
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.api.model.emptyResponseBody
import com.guanhaolin.pearch.api.model.imageResponseBody
import com.guanhaolin.pearch.api.model.responseBodyForSearchImage
import com.guanhaolin.pearch.api.model.responseBodyForSearchVideo
import com.guanhaolin.pearch.api.model.videoResponseBody
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PixabayServiceTest {
    private lateinit var pixabayService: PixabayService
    private lateinit var mockWebServer: MockWebServer

    private lateinit var responseImage: ImageResponse
    private lateinit var responseVideo: VideoResponse

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pixabayService = retrofit.create(PixabayService::class.java)

        val gson = Gson().newBuilder().create()
        responseImage = gson.fromJson(imageResponseBody, ImageResponse::class.java)
        responseVideo = gson.fromJson(videoResponseBody, VideoResponse::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchImage should return a list of images when the API call is successful`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(responseBodyForSearchImage))
        val response = pixabayService.searchImages(any(), any(), any())

        assertEquals(1, response.total)
        assertEquals(1, response.totalHits)
        assertEquals(1, response.hits.size)
        assertEquals(responseImage.id, response.hits.first().id)
    }

    @Test
    fun `searchImage should return an empty list when the API call returns no results`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(emptyResponseBody))
        val response = pixabayService.searchImages(any(), any(), any())

        assertEquals(0, response.total)
        assertEquals(0, response.totalHits)
        assertEquals(0, response.total)
    }

    @Test
    fun `searchVideo should return a list of videos when the API call is successful`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(responseBodyForSearchVideo))
        val response = pixabayService.searchVideos(any(), any(), any())

        assertEquals(1, response.total)
        assertEquals(1, response.totalHits)
        assertEquals(1, response.hits.size)
        assertEquals(responseVideo.id, response.hits.first().id)
    }

    @Test
    fun `searchVideo should return an empty list when the API call returns no results`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(emptyResponseBody))
        val response = pixabayService.searchVideos(any(), any(), any())

        assertEquals(0, response.total)
        assertEquals(0, response.totalHits)
        assertEquals(0, response.total)
    }
}
