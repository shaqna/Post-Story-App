package com.ngedev.postcat.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.auth.AuthUseCase

class AuthViewModel(private val useCase: AuthUseCase): ViewModel() {

    fun login(request: LoginRequest): LiveData<Resource<LoginResponse>> =
        useCase.login(request).asLiveData()

    fun createUser(request: RegisterRequest): LiveData<Resource<RegisterResponse>> =
        useCase.createUser(request).asLiveData()

}