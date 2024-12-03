package com.guanhaolin.pearch.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guanhaolin.pearch.api.QueryApi
import com.guanhaolin.pearch.api.QueryApi.ApiCallback
import com.guanhaolin.pearch.model.ImageInfoResponse
import com.guanhaolin.pearch.model.QueryResponse
import com.guanhaolin.pearch.model.VideoInfoResponse
import java.util.LinkedList

class MediaViewModel(app: Application) : AndroidViewModel(app) {
    private var mIsPhotosLoading = false
    private var mIsNoMorePhotos = false
    private var mIsVideosLoading = false
    private var mIsNoMoreVideos = false

    private val mQuery = MutableLiveData("")
    private var mPagePhotos = 0
    private var mPageVideos = 0
    private val mPhotos = MutableLiveData<MutableList<ImageInfoResponse>?>(LinkedList())
    private val mVideos = MutableLiveData<MutableList<VideoInfoResponse>?>(LinkedList())

    val query: LiveData<String>
        get() = mQuery

    val photos: LiveData<MutableList<ImageInfoResponse>?>
        get() = mPhotos

    val videos: LiveData<MutableList<VideoInfoResponse>?>
        get() = mVideos

    fun queryPhotos(query: String) {
        mIsNoMorePhotos = false
        val photos = mPhotos.value
        photos!!.clear()
        mPhotos.value = photos
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

        QueryApi.searchImage(
            getApplication(),
            query,
            mPagePhotos,
            object : ApiCallback<ImageInfoResponse?> {
                override fun onResponse(q: String?, response: QueryResponse<ImageInfoResponse?>?) {
                    if (query != q) return

                    val photos = mPhotos.value
                    photos!!.addAll(response?.hits?.filterNotNull() ?: listOf())
                    mPhotos.value = photos

                    mIsPhotosLoading = false
                }

                override fun onErrorResponse(q: String, error: String) {
                    if (query != q) return
                    if (error.contains("out of valid range")) mIsNoMorePhotos = true
                    mIsPhotosLoading = false
                }
            })
    }

    fun queryVideos(query: String) {
        mIsNoMoreVideos = false
        val videos = mVideos.value
        videos!!.clear()
        mVideos.value = videos
        mQuery.value = query
        mPageVideos = 1
        queryVideos()
    }

    fun loadMoreVideos() {
        if (mIsVideosLoading) return
        if (mIsNoMoreVideos) return
        mPageVideos++
        queryVideos()
    }

    private fun queryVideos() {
        mIsVideosLoading = true

        val query = mQuery.value

        QueryApi.searchVideo(
            getApplication(),
            query,
            mPageVideos,
            object : ApiCallback<VideoInfoResponse?> {
                override fun onResponse(q: String?, response: QueryResponse<VideoInfoResponse?>?) {
                    if (query != q) return

                    val videos = mVideos.value
                    videos!!.addAll(response?.hits?.filterNotNull() ?: listOf())
                    mVideos.value = videos

                    mIsVideosLoading = false
                }

                override fun onErrorResponse(q: String, error: String) {
                    if (query != q) return
                    if (error.contains("out of valid range")) mIsNoMoreVideos = true
                    mIsVideosLoading = false
                }
            })
    }
}
