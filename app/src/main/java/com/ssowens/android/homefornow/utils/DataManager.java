package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.models.HotelSearchResponse;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sheila Owens on 7/24/18.
 */
public class DataManager {

    private static final String PEXELS_ENDPOINT = "https://api.pexels.com";
    protected static DataManager sDataManager;
    private Retrofit retrofit;

    public static DataManager get(Context context) {
        if (sDataManager == null) {
            Gson gson = new GsonBuilder()

                    // Inform the GsonBuilder that there is a custom deserializer by
                    // registering a type adapter, passing in the class it will
                    // output and the deserializer it should use to create the class.
                    .registerTypeAdapter(HotelSearchResponse.class,
                            new PhotoListDeserializer())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PEXELS_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sDataManager;
    }

    DataManager() {
        throw new InstantiationError("Default constructor called for singleton");
    }

    private static Interceptor sRequestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url().newBuilder()
                    .addQueryParameter("authorization", BuildConfig.PexelsApiKey)
                    .build();

            Request request = chain.request().newBuilder()
                    .url(url)
                    .build();

            return chain.proceed(request);
        }
    };
}
