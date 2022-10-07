package com.ngedev.postcat.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import com.ngedev.postcat.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
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

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var storyUseCase: StoryUseCase

    @Mock
    private lateinit var storiesObserver: Observer<PagingData<Story>>

    @Mock
    private lateinit var fakeList: PagingData<Story>

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(storyUseCase)
    }


    @Test
    fun `get all stories successfully`() = runTest {
        val flowData: Flow<PagingData<Story>> = flow { fakeList }

        `when`(storyUseCase.getAllStories()).thenReturn(flowData)
        homeViewModel.getAllStories().observeForever(storiesObserver)

        verify(storyUseCase).getAllStories()
    }

}