package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.models.HotelSearchResponse;
import com.ssowens.android.homefornow.models.Photos;
import com.ssowens.android.homefornow.remote.ApiService;

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

import static com.ssowens.android.homefornow.remote.Config.PEXELS_API_KEY;

/**
 * Created by Sheila Owens on 7/24/18.
 */
public class DataManager {

    private static final String PEXELS_ENDPOINT = "https://api.pexels.com";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    protected static DataManager sDataManager;
    private Retrofit retrofit;
    private static final String HOTELS_SEARCH = "hotels";
    private List<Photos> photosList;
    private List<HotelSearchListener> hotelSearchListenerList;

    public DataManager(Retrofit retrofit) {
        // TODO where does the tokenstore come from
        this.retrofit = retrofit;
        hotelSearchListenerList = new ArrayList<>();
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
                    .registerTypeAdapter(HotelSearchResponse.class,
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

        }

        return sDataManager;
    }

    DataManager() {
        throw new InstantiationError("Default constructor called for singleton");
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

    public void fetchHotelSearch() {
        ApiService apiService = retrofit
                .create(ApiService.class);
        apiService.getImages(HOTELS_SEARCH)
                .enqueue((new Callback<HotelSearchResponse>() {
                    @Override
                    public void onResponse(Call<HotelSearchResponse> call,
                                           retrofit2.Response<HotelSearchResponse> response) {
                        photosList = response.body().getPhotosList();

                    }

                    @Override
                    public void onFailure(Call<HotelSearchResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel search" + "~" + t);
                    }
                }));
    }

    // TODO remove the other getPhotoList in Photos
    public List<Photos> getPhotosList() {
        return photosList;
    }


}
