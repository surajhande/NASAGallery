package com.suraj.nasagallery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suraj.nasagallery.data.ImageModel
import com.suraj.nasagallery.data.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class ImagesUiModel() {
    object Loading : ImagesUiModel()
    object Error : ImagesUiModel()
    data class Success(val result: ArrayList<ImageModel> = arrayListOf()) : ImagesUiModel()
}

@HiltViewModel
class ImagesViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private val _imagesUiModel = MutableStateFlow(ImagesUiModel.Success() as ImagesUiModel)
    val imagesUiModel: StateFlow<ImagesUiModel> = _imagesUiModel

    val isInfoVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)

    private var imageList: ArrayList<ImageModel> = arrayListOf()
    var currentPosition = 0

    init {
        loadImagesFromJson()
    }

    private fun loadImagesFromJson() = viewModelScope.launch {
        _imagesUiModel.value = ImagesUiModel.Loading
        withContext(Dispatchers.Default) {
             val result = imageRepository.loadImagesFromJson()
             if (result.isEmpty())
                 _imagesUiModel.value = ImagesUiModel.Error
            else {
                 _imagesUiModel.value = ImagesUiModel.Success(result)
                imageList = result
             }
        }
    }

    fun getImages(): ArrayList<ImageModel> {
        if (imageList.isNullOrEmpty()) {
            loadImagesFromJson()
        }
        return imageList
    }
}