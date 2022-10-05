package com.ngedev.postcat.domain.usecase.maps

import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.domain.repository.StoryRepository
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow


class MapsInteractor(private val repository: StoryRepository): MapsUseCase {
    override fun getAllStoriesWithLocation(): Flow<Resource<StoriesResponse>> =
        repository.getAllStoriesWithLocation()
}