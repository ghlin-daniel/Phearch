package com.brickgit.imagesearch.model;

public class Count {

	private String mCount;

	public Count(int count) {
		if (count < 1000) {
			mCount = String.valueOf(count);
		}
		else {
			mCount = String.format("%dK", count / 1000);
		}
	}

	public String getCount() {
		return mCount;
	}
}
