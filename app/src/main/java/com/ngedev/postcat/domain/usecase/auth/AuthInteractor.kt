package com.ngedev.postcat.domain.usecase.auth

import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.domain.repository.AuthRepository
import com.ngedev.postcat.domain.state.Resource
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val repository: AuthRepository): AuthUseCase {
    override fun login(request: LoginRequest): Flow<Resource<LoginResponse>> =
        repository.login(request)

    override fun createUser(request: RegisterRequest): Flow<Resource<RegisterResponse>> =
        repository.createUser(request)
}