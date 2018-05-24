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

	private boolean mIsLoading = false;
	private boolean mIsNoMore = false;

	private String mQuery;
	private int mPage = 0;
	private MutableLiveData<List<ImageInfoResponse>> mPhotos;

	public MediaViewModel(@NonNull Application app) {
		super(app);
	}

	public LiveData<List<ImageInfoResponse>> getPhotos() {
		if (mPhotos == null) {
			mPhotos = new MutableLiveData<>();
			mPhotos.setValue(new LinkedList<ImageInfoResponse>());
		}

		return mPhotos;
	}


	public void queryPhotos(String query) {
		mIsNoMore = false;
		List<ImageInfoResponse> photos = mPhotos.getValue();
		photos.clear();
		mPhotos.setValue(photos);
		mQuery = query;
		mPage = 1;
		query();
	}

	public void loadMorePhotos() {
		if (mIsLoading) return;
		if (mIsNoMore) return;
		mPage++;
		query();
	}

	private void query() {
		mIsLoading = true;

		QueryApi.searchImage(getApplication(), mQuery, mPage, new QueryApi.ApiCallback() {
			@Override
			public void onResponse(String query, QueryResponse response) {
				if (!mQuery.equals(query)) return;

				if (response.getHits().size() != 0) {
					List<ImageInfoResponse> photos = mPhotos.getValue();
					photos.addAll(response.getHits());
					mPhotos.setValue(photos);
				}

				mIsLoading = false;
			}

			@Override
			public void onErrorResponse(String query, String error) {
				if (!mQuery.equals(query)) return;
				if (error.contains("out of valid range")) mIsNoMore = true;
				mIsLoading = false;
			}
		});
	}
}
