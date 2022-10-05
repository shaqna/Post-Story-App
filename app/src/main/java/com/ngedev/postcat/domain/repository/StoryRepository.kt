package com.ngedev.postcat.domain.repository

import androidx.paging.PagingData
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryRepository {
    fun getAllStories(): Flow<PagingData<Story>>

    fun getAllStoriesWithLocation(): Flow<Resource<StoriesResponse>>

    fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Flow<Resource<PostStoryResponse>>
}