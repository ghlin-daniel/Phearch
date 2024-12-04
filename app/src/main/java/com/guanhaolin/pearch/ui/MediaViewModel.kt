package com.guanhaolin.pearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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
    val query: LiveData<String> = _query.asLiveData()

    private var _isPhotosLoading = false
    private var _isNoMorePhotos = false
    private var _pagePhotos = 0
    private val _photos = MutableLiveData<List<ImageResponse>>(listOf())
    val photos: LiveData<List<ImageResponse>> = _photos

    private var _isVideosLoading = false
    private var _isNoMoreVideos = false
    private var _pageVideos = 0
    private val _videos = MutableLiveData<List<VideoResponse>>(listOf())
    val videos: LiveData<List<VideoResponse>> = _videos

    init {
        viewModelScope.launch {
            _query.debounce(800)
                .collectLatest {
                    _isNoMorePhotos = false
                    _isPhotosLoading = false
                    _pagePhotos = 1
                    _photos.value = listOf()

                    _isNoMoreVideos = false
                    _isVideosLoading = false
                    _pageVideos = 1
                    _videos.value = listOf()

                    if (it.isNotEmpty()) {
                        queryPhotos()
                        queryVideos()
                    }
                }
        }
    }

    fun query(query: String) {
        _query.value = query
    }

    fun loadMorePhotos() {
        if (_isPhotosLoading) return
        if (_isNoMorePhotos) return
        _pagePhotos++
        queryPhotos()
    }

    fun loadMoreVideos() {
        if (_isVideosLoading) return
        if (_isNoMoreVideos) return
        _pagePhotos++
        queryVideos()
    }

    private fun queryPhotos() {
        _query.value?.let { currentQuery ->
            viewModelScope.launch {
                _isPhotosLoading = true
                mediaRepository.searchImages(currentQuery, _pagePhotos).collect {
                    if (currentQuery == _query.value) {
                        val photos = _photos.value?.toMutableList() ?: mutableListOf()
                        photos.addAll(it)
                        _photos.postValue(photos)
                    }
                    _isPhotosLoading = false
                }
            }
        }
    }

    private fun queryVideos() {
        _query.value?.let { currentQuery ->
            viewModelScope.launch {
                _isVideosLoading = true
                mediaRepository.searchVideos(currentQuery, _pageVideos).collect {
                    if (currentQuery == _query.value) {
                        val videos = _videos.value?.toMutableList() ?: mutableListOf()
                        videos.addAll(it)
                        _videos.postValue(videos)
                    }
                    _isVideosLoading = false
                }
            }
        }
    }
}
