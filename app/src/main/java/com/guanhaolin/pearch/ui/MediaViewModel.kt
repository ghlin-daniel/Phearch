package com.guanhaolin.pearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guanhaolin.pearch.api.model.ImageResponse
import com.guanhaolin.pearch.data.MediaRepository
import com.guanhaolin.pearch.model.VideoInfoResponse
import kotlinx.coroutines.launch
import java.util.LinkedList

class MediaViewModel(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    private var mIsPhotosLoading = false
    private var mIsNoMorePhotos = false
    private var mIsVideosLoading = false
    private var mIsNoMoreVideos = false

    private val mQuery = MutableLiveData("")
    private var mPagePhotos = 0
    private var mPageVideos = 0
    private val mVideos = MutableLiveData<MutableList<VideoInfoResponse>?>(LinkedList())

    private val _photos = MutableLiveData<List<ImageResponse>>(listOf())
    val photos: LiveData<List<ImageResponse>> = _photos

    val query: LiveData<String>
        get() = mQuery

    val videos: LiveData<MutableList<VideoInfoResponse>?>
        get() = mVideos

    fun queryPhotos(query: String) {
        mIsNoMorePhotos = false
        _photos.value = listOf()
        mQuery.value = query
        mPagePhotos = 1
        queryPhotos()
    }

    fun loadMorePhotos() {
        if (mIsPhotosLoading) return
        if (mIsNoMorePhotos) return
        mPagePhotos++
        queryPhotos()
    }

    private fun queryPhotos() {
        mIsPhotosLoading = true

        val query = mQuery.value

        viewModelScope.launch {
            mediaRepository.searchImages(query!!, mPagePhotos).collect {
                if (query == mQuery.value) {
                    val photos = _photos.value?.toMutableList() ?: mutableListOf()
                    photos.addAll(it)
                    _photos.postValue(photos)
                }
                mIsPhotosLoading = false
            }
        }

//    fun queryVideos(query: String) {
//        mIsNoMoreVideos = false
//        val videos = mVideos.value
//        videos!!.clear()
//        mVideos.value = videos
//        mQuery.value = query
//        mPageVideos = 1
//        queryVideos()
//    }
//
//    fun loadMoreVideos() {
//        if (mIsVideosLoading) return
//        if (mIsNoMoreVideos) return
//        mPageVideos++
//        queryVideos()
//    }

//    private fun queryVideos() {
//        mIsVideosLoading = true
//
//        val query = mQuery.value
//
//        QueryApi.searchVideo(
//            getApplication(),
//            query,
//            mPageVideos,
//            object : ApiCallback<VideoInfoResponse?> {
//                override fun onResponse(q: String?, response: QueryResponse<VideoInfoResponse?>?) {
//                    if (query != q) return
//
//                    val videos = mVideos.value
//                    videos!!.addAll(response?.hits?.filterNotNull() ?: listOf())
//                    mVideos.value = videos
//
//                    mIsVideosLoading = false
//                }
//
//                override fun onErrorResponse(q: String, error: String) {
//                    if (query != q) return
//                    if (error.contains("out of valid range")) mIsNoMoreVideos = true
//                    mIsVideosLoading = false
//                }
//            })
    }
}
