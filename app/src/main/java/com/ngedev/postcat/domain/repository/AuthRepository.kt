package com.ngedev.postcat.domain.repository

import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.LoginResult
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>
    fun createUser(registerRequest: RegisterRequest): Flow<Resource<RegisterResponse>>
}