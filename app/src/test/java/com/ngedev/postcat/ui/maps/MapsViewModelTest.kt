package com.ngedev.postcat.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.maps.MapsUseCase
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import com.ngedev.postcat.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: MapsViewModel

    @Mock
    private lateinit var mapsUseCase: MapsUseCase

    @Mock
    private lateinit var storiesObserver: Observer<Resource<StoriesResponse>>

    @Mock
    private lateinit var fakeList: Resource<StoriesResponse>

    @Before
    fun setUp() {
        viewModel = MapsViewModel(mapsUseCase)
    }


    @Test
    fun `get all stories with location successfully`() = runTest {
        val flowData: Flow<Resource<StoriesResponse>> =
            flowOf(Resource.Success(DataDummy.generateDummyStoriesResponse()))

        Mockito.`when`(mapsUseCase.getAllStoriesWithLocation()).thenReturn(flowData)

        assertEquals(
            DataDummy.generateDummyStoriesResponse(),
            viewModel.getAllStoriesWithLocation().getOrAwaitValue().data
        )

        Mockito.verify(mapsUseCase).getAllStoriesWithLocation()
    }
}