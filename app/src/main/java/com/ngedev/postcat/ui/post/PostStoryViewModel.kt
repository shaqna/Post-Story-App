package com.ngedev.postcat.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngedev.postcat.data.source.response.PostStoryResponse
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostStoryViewModel(private val useCase: StoryUseCase): ViewModel() {

    private val _isError = MutableLiveData<String>()
    val isError = _isError

    private val  _isSuccess = MutableLiveData<PostStoryResponse>()
    val isSuccess = _isSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    fun uploadStory(photo: MultipartBody.Part, description: RequestBody, lat: RequestBody?, lon: RequestBody?){
        viewModelScope.launch {
            _isLoading.value = true
            useCase.uploadStory(photo, description, lat, lon).collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        resource.data?.let { response ->
                            _isSuccess.value = response
                        }
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _isError.value = resource.message.toString()
                    }

                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }


}