package com.ngedev.postcat.data.source.service

import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.data.source.response.StoriesResponse
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.utils.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST(Constants.REGISTER_URL)
    suspend fun createUser(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST(Constants.LOGIN_URL)
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET(Constants.STORIES_URL)
    suspend fun getAllStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null
    ): StoriesResponse

    @Multipart
    @POST(Constants.STORIES_URL)
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null
    ): PostStoryResponse

}