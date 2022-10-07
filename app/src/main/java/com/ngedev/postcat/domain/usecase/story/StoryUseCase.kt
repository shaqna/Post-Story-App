package com.ngedev.postcat.domain.usecase.story

import androidx.paging.PagingData
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.domain.model.Story
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryUseCase {
    fun getAllStories(): Flow<PagingData<Story>>

    fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Flow<Resource<PostStoryResponse>>
}