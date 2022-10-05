package com.ngedev.postcat.data.source.service

import android.util.Log
import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.LoginResult
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRemote(private val apiService: ApiService) {

    fun createUser(registerRequest: RegisterRequest): Flow<ApiResponse<RegisterResponse>> = flow {
        try {
            val response = apiService.createUser(registerRequest)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun login(loginRequest: LoginRequest): Flow<ApiResponse<LoginResponse>> = flow {
        try {
            Log.d("MY_REQUEST", loginRequest.toString())
            val response = apiService.login(loginRequest)
            Log.d("MY_RESPONSE", response.toString())

            emit(ApiResponse.Success(response))

        }catch (e: Exception) {
            Log.e("MY_RESPONSE_ERROR", e.toString())
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}