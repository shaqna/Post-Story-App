package com.ngedev.postcat.data.source

import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkRequest<RequestType> {
    private val result: Flow<Resource<RequestType>> = flow {
        emit(Resource.Loading())
        when(val response = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(fetchResult(response.data)))
                onFetchSuccess()
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.message))
            }
            else -> {}
        }
    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun fetchResult(result: RequestType): RequestType

    protected open suspend fun onFetchSuccess() {}

    protected open suspend fun onFetchFailed() {}

    fun asFlow() = result
}