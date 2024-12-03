package com.guanhaolin.pearch.api

import app.cash.turbine.test
import com.guanhaolin.pearch.api.model.SearchResultResponse
import com.guanhaolin.pearch.data.MediaRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

    @Before
    fun setUp() {
        pixabayService = mockk()
    }

    @Test
    fun `searchImages should return search result when API call is successful`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val mediaRepository = MediaRepositoryImpl(pixabayService, dispatcher)

        coEvery { pixabayService.searchImages(any(), any(), any()) } returns SearchResultResponse(
            0,
            0,
            listOf()
        )

        mediaRepository.searchImages("android", 1).test {
            val result = awaitItem()
            assertTrue(result.isEmpty())
            awaitComplete()
            coVerify { pixabayService.searchImages(any(), any(), any()) }
        }
    }
}
