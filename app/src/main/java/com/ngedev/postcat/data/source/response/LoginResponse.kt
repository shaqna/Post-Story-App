package com.ngedev.postcat.data.source.response

data class LoginResponse(
    val error: Boolean,

    val message: String,

    val loginResult: LoginResult
)
