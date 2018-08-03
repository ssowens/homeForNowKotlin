package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedResponse;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.services.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Sheila Owens on 7/24/18.
 */
public class DataManager {

    private static final String PEXELS_ENDPOINT = "https://api.pexels.com/v1/";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PEXELS_API_KEY = BuildConfig.PexelsApiKey;
    protected static DataManager sDataManager;
    private Retrofit retrofit;
    private static final String HOTELS_SEARCH = "hotels";
    private List<Photo> photoList;
    private List<HotelSearchListener> hotelSearchListenerList;

    DataManager(Retrofit retrofit) {
        this.retrofit = retrofit;
        hotelSearchListenerList = new ArrayList<>();
    }

    DataManager() {
        throw new InstantiationError("Default constructor called for singleton");
    }

    public void addHotelSearchListener(HotelSearchListener listener) {
        hotelSearchListenerList.add(listener);
    }

    public void removeHotelSearchListener(HotelSearchListener listener) {
        hotelSearchListenerList.remove(listener);
    }

    private void notifySearchListeners() {
        for (HotelSearchListener listener : hotelSearchListenerList) {
            listener.onHotelSearchFinished();
        }
    }

    public static DataManager get(Context context) {
        if (sDataManager == null) {
            Gson gson = new GsonBuilder()

                    // Inform the GsonBuilder that there is a custom deserializer by
                    // registering a type adapter, passing in the class it will
                    // output and the deserializer it should use to create the class.
                    .registerTypeAdapter(HotelPopularSearchResponse.class,
                            new PhotoListDeserializer())
                    .create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(sRequestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PEXELS_ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            sDataManager = new DataManager(retrofit);
        }
        return sDataManager;
    }


    // The Interceptor is used for a parameters that need to be added to the
    // request
    private static Interceptor sRequestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url().newBuilder()
                    .build();

            Request request = chain.request().newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, PEXELS_API_KEY)
                    .url(url)
                    .build();

            return chain.proceed(request);
        }
    };

    public void fetchHotelPopularSearch() {
        ApiService apiService = retrofit
                .create(ApiService.class);
        apiService.hotelsSearhPopular(HOTELS_SEARCH)
                // Handles web request asynchronously
                .enqueue(new Callback<HotelPopularSearchResponse>() {
                    @Override
                    public void onResponse(Call<HotelPopularSearchResponse> call,
                                           retrofit2.Response<HotelPopularSearchResponse> response) {

                        photoList = response.body().getPhotoList();
                        Timber.i("Sheila popular photoList = %s", photoList.toString());
                        notifySearchListeners();

                    }

                    @Override
                    public void onFailure(Call<HotelPopularSearchResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel search" + " ~ " + t);
                    }
                });
    }

    public void fetchHotelTopRatedSearch() {
        ApiService apiService = retrofit
                .create(ApiService.class);
        apiService.hotelsSearchTopRated(HOTELS_SEARCH)
                // Handles web request asynchronously
                .enqueue(new Callback<HotelTopRatedResponse>() {
                    @Override
                    public void onResponse(Call<HotelTopRatedResponse> call,
                                           retrofit2.Response<HotelTopRatedResponse> response) {

                        photoList = response.body().getHotelTopRatedList();
                        Timber.i("Sheila top rated photoList = %s", photoList.toString());
                        notifySearchListeners();

                    }

                    @Override
                    public void onFailure(Call<HotelTopRatedResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel search" + " ~ " + t);
                    }
                });
    }

    // TODO remove the other getPhotoList in Photo
    public List<Photo> getPhotoList() {
        return photoList;
    }

    public Photo getHotelPhoto(String photoId) {
        for (Photo photo : photoList) {
            if (photo.getId().equals(photoId)) {
                return photo;
            }
        }
        return null;
    }

}
