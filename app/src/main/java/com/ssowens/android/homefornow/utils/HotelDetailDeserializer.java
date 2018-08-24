package com.ssowens.android.homefornow.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ssowens.android.homefornow.models.HotelDetailResponse;

import java.lang.reflect.Type;

/**
 * Created by Sheila Owens on 8/19/18.
 */
public class HotelDetailDeserializer implements JsonDeserializer<HotelDetailResponse> {
    @Override
    public HotelDetailResponse deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Gson().fromJson(jsonObject, HotelDetailResponse.class);
    }
}
