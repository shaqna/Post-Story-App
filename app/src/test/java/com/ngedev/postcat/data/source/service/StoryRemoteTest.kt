package com.ngedev.postcat.data.source.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class StoryRemoteTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var storyRemote: StoryRemote

    @Before
    fun setUp() {
        storyRemote = StoryRemote(apiService)
    }

    @Mock
    private lateinit var photo: MultipartBody.Part

    @Mock
    private lateinit var description: RequestBody

    @Mock
    private lateinit var lat: RequestBody

    @Mock
    private lateinit var lon: RequestBody


    @Test
    fun `is get stories paging successfully`() = runTest {
        val expectedSuccessResponse = DataDummy.generateDummyStoriesResponse()
        Mockito.`when`(apiService.getAllStories(page = 10, size = 10)).thenReturn(expectedSuccessResponse)
        val actualResponse = storyRemote.getAllStories(page = 10, size = 10)
        assertNotNull(actualResponse)
        assertEquals(expectedSuccessResponse, actualResponse)
        assertTrue(actualResponse.listStory.isNotEmpty())
    }

    @Test
    fun `is get stories with location successfully`() = runTest {
        val expectedSuccessResponse = DataDummy.generateDummyStoriesResponse()

        Mockito.`when`(apiService.getAllStories(location = 1)).thenReturn(expectedSuccessResponse)

        storyRemote.getAllStoriesWithLocation().collect {
            when(it) {
                is ApiResponse.Success -> {
                    assertTrue(it.data.listStory.isNotEmpty())
                    assertNotNull(it)
                    assertEquals(expectedSuccessResponse, it.data)
                }
                else -> {}
            }
        }

    }

    @Test
    fun `is post story successfully`() = runTest {
        val expectedSuccessResponse = DataDummy.generateDummyPostStoryResponse()

        Mockito.`when`(apiService.uploadStory(photo,description, lat, lon)).thenReturn(expectedSuccessResponse)

        storyRemote.uploadStory(photo, description, lat, lon).collect {
            when(it) {
                is ApiResponse.Success -> {
                    assertTrue(!it.data.error)
                }
                else -> {}
            }
        }
    }

}