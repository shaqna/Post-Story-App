package com.ngedev.postcat.ui.post

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import com.ngedev.postcat.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PostStoryViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: PostStoryViewModel

    @Mock
    private lateinit var useCase: StoryUseCase

    @Mock
    private lateinit var photo: MultipartBody.Part

    @Mock
    private lateinit var description: RequestBody

    @Mock
    private lateinit var lat: RequestBody

    @Mock
    private lateinit var lon: RequestBody

    @Mock
    private lateinit var fakePostResponse: Resource<PostStoryResponse>

    @Mock
    private lateinit var successObserver: Observer<PostStoryResponse>

    @Before
    fun setUp() {
        viewModel = PostStoryViewModel(useCase)
    }

    @Test
    fun `upload story successfully`() = runTest {
        val expectedResponse = DataDummy.generateDummyPostStoryResponse()

        val flowData: Flow<Resource<PostStoryResponse>> =
            flowOf(Resource.Success(DataDummy.generateDummyPostStoryResponse()))

        `when`(useCase.uploadStory(photo, description, lat, lon)).thenReturn(flowData)
        viewModel.uploadStory(photo, description, lat, lon).apply {
            advanceUntilIdle()
        }
        assertEquals(expectedResponse, viewModel.isSuccess.getOrAwaitValue())

        Mockito.verify(useCase).uploadStory(photo, description, lat, lon)
    }

}