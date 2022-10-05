package com.ngedev.postcat.data.source.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val error: Boolean,

    val message: String,

    val loginResult: LoginResult
)
