package com.ngedev.postcat.data.source.response

import com.google.gson.annotations.SerializedName

data class LoginResult(
    val userId: String,

    val name: String,

    val token: String
)
