package com.ngedev.postcat.domain.model

import java.io.File

data class PostStoryRequest(
    val description: String,
    val photo: File
)
