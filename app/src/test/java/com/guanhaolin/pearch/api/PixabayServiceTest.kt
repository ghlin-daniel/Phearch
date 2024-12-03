package com.guanhaolin.pearch.api

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PixabayServiceTest {
    private lateinit var pixabayService: PixabayService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        pixabayService = retrofit.create(PixabayService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchImage should return a list of images when the API call is successful`() = runTest {
        val responseBody = """
            {
              "total": 1,
              "totalHits": 1,
              "hits": [
                {
                  "id": 3994154,
                  "pageURL": "https://pixabay.com/photos/clouds-weather-cloudscape-dramatic-3994154/",
                  "type": "photo",
                  "tags": "clouds, weather, sky background",
                  "previewURL": "https://cdn.pixabay.com/photo/2019/02/13/11/17/clouds-3994154_150.jpg",
                  "previewWidth": 92,
                  "previewHeight": 150,
                  "webformatURL": "https://pixabay.com/get/g2198cffb213b1466c6d2311524799c0494fc5fac0c0b2da19a0a25280ea6a27f060f80c1633619b94ac9e3d9f200c24beba8197ea4b85b17529d471d41c3e5fe_640.jpg",
                  "webformatWidth": 392,
                  "webformatHeight": 640,
                  "largeImageURL": "https://pixabay.com/get/g33f1032e6d905708c9e9490d7773ad5fac052782f5dc8a9f11b00c7a99451576426fc74323c46ee14e8a6ad7a9391cfbbdc75c45d5a802aea37e2448280f604f_1280.jpg",
                  "imageWidth": 1961,
                  "imageHeight": 3200,
                  "imageSize": 1024888,
                  "views": 119109,
                  "downloads": 96314,
                  "collections": 3412,
                  "likes": 228,
                  "comments": 28,
                  "user_id": 356019,
                  "user": "sandid",
                  "userImageURL": "https://cdn.pixabay.com/user/2015/02/23/11-47-07-191_250x250.jpg"
                }
              ]
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        val response = pixabayService.searchImages(any(), any(), any())

        assertEquals(1, response.total)
        assertEquals(1, response.totalHits)
        assertEquals(1, response.total)
    }

    @Test
    fun `searchImage should return an empty list when the API call returns no results`() = runTest {
        val responseBody = """
            {
              "total": 0,
              "totalHits": 0,
              "hits": []
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseBody))
        val response = pixabayService.searchImages(any(), any(), any())

        assertEquals(0, response.total)
        assertEquals(0, response.totalHits)
        assertEquals(0, response.total)
    }
}
