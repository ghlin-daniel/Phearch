package com.brickgit.imagesearch.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.brickgit.imagesearch.api.QueryApi;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel Lin on 5/22/18.
 */
public class MediaViewModel extends AndroidViewModel {

	private boolean mIsPhotosLoading = false;
	private boolean mIsNoMorePhotos = false;
	private boolean mIsVideosLoading = false;
	private boolean mIsNoMoreVideos = false;

	private MutableLiveData<String> mQuery;
	private int mPagePhotos = 0;
	private int mPageVideos = 0;
	private MutableLiveData<List<ImageInfoResponse>> mPhotos;
	private MutableLiveData<List<VideoInfoResponse>> mVideos;

	public MediaViewModel(@NonNull Application app) {
		super(app);
	}

	public LiveData<String> getQuery() {
		if (mQuery == null) {
			mQuery = new MutableLiveData<>();
		}
		return mQuery;
	}

	public LiveData<List<ImageInfoResponse>> getPhotos() {
		if (mPhotos == null) {
			mPhotos = new MutableLiveData<>();
			mPhotos.setValue(new LinkedList<>());
		}
		return mPhotos;
	}

	public LiveData<List<VideoInfoResponse>> getVideos() {
		if (mVideos == null) {
			mVideos = new MutableLiveData<>();
			mVideos.setValue(new LinkedList<>());
		}
		return mVideos;
	}

	public void queryPhotos(String query) {
		mIsNoMorePhotos = false;
		List<ImageInfoResponse> photos = mPhotos.getValue();
		photos.clear();
		mPhotos.setValue(photos);
		mQuery.setValue(query);
		mPagePhotos = 1;
		queryPhotos();
	}

	public void loadMorePhotos() {
		if (mIsPhotosLoading) return;
		if (mIsNoMorePhotos) return;
		mPagePhotos++;
		queryPhotos();
	}

	private void queryPhotos() {
		mIsPhotosLoading = true;

		final String query = mQuery.getValue();

		QueryApi.searchImage(getApplication(), query, mPagePhotos, new QueryApi.ApiCallback<ImageInfoResponse>() {
			@Override
			public void onResponse(String q, QueryResponse<ImageInfoResponse> response) {
				if (!query.equals(q)) return;

				List<ImageInfoResponse> photos = mPhotos.getValue();
				photos.addAll(response.getHits());
				mPhotos.setValue(photos);

				mIsPhotosLoading = false;
			}

			@Override
			public void onErrorResponse(String q, String error) {
				if (!query.equals(q)) return;
				if (error.contains("out of valid range")) mIsNoMorePhotos = true;
				mIsPhotosLoading = false;
			}
		});
	}

	public void queryVideos(String query) {
		mIsNoMoreVideos = false;
		List<VideoInfoResponse> videos = mVideos.getValue();
		videos.clear();
		mVideos.setValue(videos);
		mQuery.setValue(query);
		mPageVideos = 1;
		queryVideos();
	}

	public void loadMoreVideos() {
		if (mIsVideosLoading) return;
		if (mIsNoMoreVideos) return;
		mPageVideos++;
		queryVideos();
	}

	private void queryVideos() {
		mIsVideosLoading = true;

		final String query = mQuery.getValue();

		QueryApi.searchVideo(getApplication(), query, mPageVideos, new QueryApi.ApiCallback<VideoInfoResponse>() {
			@Override
			public void onResponse(String q, QueryResponse<VideoInfoResponse> response) {
				if (!query.equals(q)) return;

				List<VideoInfoResponse> videos = mVideos.getValue();
				videos.addAll(response.getHits());
				mVideos.setValue(videos);

				mIsVideosLoading = false;
			}

			@Override
			public void onErrorResponse(String q, String error) {
				if (!query.equals(q)) return;
				if (error.contains("out of valid range")) mIsNoMoreVideos = true;
				mIsVideosLoading = false;
			}
		});
	}
}
