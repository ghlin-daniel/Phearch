package com.guanhaolin.pearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.api.model.VideoResponse
import com.guanhaolin.pearch.data.MediaRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MediaViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    private val _query = MutableStateFlow("")

    private val _uiState = MutableLiveData(MediaUiState())
    val uiState: LiveData<MediaUiState> = _uiState

    init {
        viewModelScope.launch {
            _query.debounce(800)
                .collectLatest {
                    _uiState.value = MediaUiState(
                        images = MediaUiModel(
                            isLoading = false,
                            hasMoreData = true,
                            currentPage = 1,
                            data = listOf()
                        ),
                        videos = MediaUiModel(
                            isLoading = false,
                            hasMoreData = true,
                            currentPage = 1,
                            data = listOf()
                        )
                    )

                    if (it.isNotEmpty()) {
                        queryPhotos(1)
                        queryVideos(1)
                    }
                }
        }
    }

    fun query(query: String) {
        _query.value = query
    }

    fun loadMorePhotos() {
        _uiState.value!!.images.apply {
            if (isLoading || !hasMoreData) return
            queryPhotos(currentPage + 1)
        }
    }

    fun loadMoreVideos() {
        _uiState.value!!.videos.apply {
            if (isLoading || !hasMoreData) return
            queryVideos(currentPage + 1)
        }
    }

    private fun queryPhotos(nextPage: Int) {
        viewModelScope.launch {
            val photosUiState = _uiState.value!!.images
            _uiState.value = _uiState.value?.copy(
                images = photosUiState.copy(
                    isLoading = true,
                )
            )
            val currentQuery = _query.value
            mediaRepository.searchImages(currentQuery, nextPage).collect {
                val photos = photosUiState.data.toMutableList()
                if (currentQuery == _query.value) {
                    photos.addAll(it)
                }
                _uiState.value = _uiState.value!!.copy(
                    images = photosUiState.copy(
                        isLoading = false,
                        hasMoreData = it.isNotEmpty(),
                        currentPage = nextPage,
                        data = photos
                    )
                )
            }
        }

    }

    private fun queryVideos(nextPage: Int) {
        viewModelScope.launch {
            val videosUiState = _uiState.value!!.videos
            _uiState.value = _uiState.value?.copy(
                videos = videosUiState.copy(
                    isLoading = true,
                )
            )
            val currentQuery = _query.value
            mediaRepository.searchVideos(currentQuery, nextPage).collect {
                val videos = videosUiState.data.toMutableList()
                if (currentQuery == _query.value) {
                    videos.addAll(it)
                }
                _uiState.value = _uiState.value!!.copy(
                    videos = videosUiState.copy(
                        isLoading = false,
                        hasMoreData = it.isNotEmpty(),
                        currentPage = nextPage,
                        data = videos
                    )
                )
            }
        }
    }

}

data class MediaUiModel<T>(
    val isLoading: Boolean = false,
    val hasMoreData: Boolean = true,
    val currentPage: Int = 0,
    val data: List<T> = listOf()
)

data class MediaUiState(
    val images: MediaUiModel<ImageResponse> = MediaUiModel(),
    val videos: MediaUiModel<VideoResponse> = MediaUiModel()
)