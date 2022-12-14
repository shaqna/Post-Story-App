package com.ngedev.postcat.data.source.response

import com.ngedev.postcat.domain.model.Story

data class StoriesResponse(
    val error: Boolean,

    val message: String,

    val listStory: List<Story>
)
