package com.ngedev.postcat.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import com.ngedev.postcat.utils.PagedTestDataSource
import com.ngedev.postcat.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var storyUseCase: StoryUseCase


    @Test
    fun `get all stories successfully`() = runTest {
        val dummyStories = DataDummy.generateDummyListStory()
        val pagingData = PagedTestDataSource.snapshot(dummyStories)

        val stories = MutableLiveData<PagingData<Story>>()
        stories.value = pagingData

        val flowData: Flow<PagingData<Story>> = flowOf(pagingData)

        `when`(storyUseCase.getAllStories()).thenReturn(flowData)
        val homeViewModel = HomeViewModel(storyUseCase)

        val actualStories = homeViewModel.getAllStories().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DiffCallBack,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutinesTestRule.testDispatcher,
            workerDispatcher = coroutinesTestRule.testDispatcher
        )

        differ.submitData(actualStories).apply {
            advanceUntilIdle()
        }

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStories.size, differ.snapshot().size)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

}