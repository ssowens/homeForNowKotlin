package com.ssowens.android.homefornow.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;

import java.lang.reflect.Type;

/**
 * Created by Sheila Owens on 8/7/18.
 */
public class TopRatedHotelListDeserializer implements JsonDeserializer<HotelTopRatedSearchResponse> {
    @Override
    public HotelTopRatedSearchResponse deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jobject = json.getAsJsonObject();
        return new Gson().fromJson(jobject, HotelTopRatedSearchResponse.class);
    }
}
