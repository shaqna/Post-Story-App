package com.ngedev.postcat.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.usecase.story.StoryUseCase

class HomeViewModel(private val useCase: StoryUseCase): ViewModel() {

    fun getAllStories(): LiveData<PagingData<Story>> =
        useCase.getAllStories().cachedIn(viewModelScope).asLiveData()
}