package com.brickgit.imagesearch.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseParser {

	private static ResponseParser instance;

	private Gson gson;

	public static synchronized ResponseParser getInstance() {
		if (instance == null) {
			instance = new ResponseParser();
		}
		return instance;
	}

	private ResponseParser() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Count.class, new CountDeserializer());
		gson = gsonBuilder.create();
	}

	public QueryResponse parseResponse(String response) {
		return gson.fromJson(response, QueryResponse.class);
	}
}
