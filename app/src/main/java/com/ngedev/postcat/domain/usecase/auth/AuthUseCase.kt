package com.ngedev.postcat.domain.usecase.auth

import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.LoginResult
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun login(request: LoginRequest): Flow<Resource<LoginResponse>>
    fun createUser(request: RegisterRequest): Flow<Resource<RegisterResponse>>
}