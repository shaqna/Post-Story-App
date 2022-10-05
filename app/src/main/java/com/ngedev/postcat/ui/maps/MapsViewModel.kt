package com.ngedev.postcat.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.maps.MapsUseCase
import com.ngedev.postcat.domain.usecase.story.StoryUseCase

class MapsViewModel(private val useCase: MapsUseCase): ViewModel() {

    fun getAllStoriesWithLocation(): LiveData<Resource<StoriesResponse>> =
        useCase.getAllStoriesWithLocation().asLiveData()
}