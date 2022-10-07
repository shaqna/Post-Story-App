package com.ngedev.postcat.data.repository

import com.ngedev.postcat.data.source.NetworkRequest
import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.data.source.service.AuthRemote
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.domain.repository.AuthRepository
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val authRemote: AuthRemote): AuthRepository {
    override fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>> =
        object : NetworkRequest<LoginResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<LoginResponse>> =
                authRemote.login(loginRequest)

            override fun fetchResult(result: LoginResponse): LoginResponse = result

        }.asFlow()

    override fun createUser(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>> =
        object : NetworkRequest<RegisterResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<RegisterResponse>> =
                authRemote.createUser(registerRequest)

            override fun fetchResult(result: RegisterResponse): RegisterResponse = result

        }.asFlow()
}