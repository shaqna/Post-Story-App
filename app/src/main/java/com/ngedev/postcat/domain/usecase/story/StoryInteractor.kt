package com.ngedev.postcat.domain.usecase.story

import androidx.paging.PagingData
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.repository.StoryRepository
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryInteractor(private val repository: StoryRepository): StoryUseCase {
    override fun getAllStories(): Flow<PagingData<Story>> =
        repository.getAllStories()



    override fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Flow<Resource<PostStoryResponse>> =
        repository.uploadStory(photo, description, lat, lon)
}