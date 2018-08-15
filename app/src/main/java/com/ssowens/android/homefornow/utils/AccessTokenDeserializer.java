package com.ssowens.android.homefornow.utils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ssowens.android.homefornow.models.AmadeusAccessTokenResponse;

import java.lang.reflect.Type;

/**
 * Created by Sheila Owens on 8/11/18.
 */
public class AccessTokenDeserializer implements JsonDeserializer<AmadeusAccessTokenResponse> {
    @Override
    public AmadeusAccessTokenResponse deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Gson().fromJson(json, AmadeusAccessTokenResponse.class);
    }
}
