package com.ngedev.postcat.data.source.service

import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.data.source.response.StoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRemote(private val service: ApiService) {
    suspend fun getAllStories(page: Int, size: Int): StoriesResponse =
        service.getAllStories(page, size)

    suspend fun getAllStoriesWithLocation(): Flow<ApiResponse<StoriesResponse>> =
        flow {
            try {
                val response = service.getAllStories(location = 1)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun uploadStory(
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Flow<ApiResponse<PostStoryResponse>> =
        flow {
            try {
                val response = service.uploadStory(photo, description, lat, lon)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}