package com.ngedev.postcat.data.source.service

import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.data.source.response.LoginResponse
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
            val response = apiService.login(loginRequest)

            emit(ApiResponse.Success(response))

        }catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}