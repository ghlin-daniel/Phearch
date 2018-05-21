package com.brickgit.imagesearch.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class CountDeserializer implements JsonDeserializer {
	@Override
	public Count deserialize(
			JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		final int count = json.getAsInt();
		return new Count(count);
	}
}
