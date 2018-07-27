package com.ssowens.android.homefornow.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ssowens.android.homefornow.models.HotelSearchResponse;

import java.lang.reflect.Type;

/**
 * Created by Sheila Owens on 7/23/18.
 */
public class PhotoListDeserializer implements JsonDeserializer<HotelSearchResponse> {

    @Override
    public HotelSearchResponse deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // TODO LOOP
        JsonElement responseElement = json.getAsJsonArray().get(0);
        return new Gson().fromJson(responseElement, HotelSearchResponse.class);
    }
}
