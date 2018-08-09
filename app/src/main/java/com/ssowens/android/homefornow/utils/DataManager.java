package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.listeners.HotelTopRatedSearchListener;
import com.ssowens.android.homefornow.models.Data;
import com.ssowens.android.homefornow.models.HotelOffersResponse;
import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedPhoto;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.services.ApiService;
import com.ssowens.android.homefornow.services.HotelOffersApi;

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
    private static final String AMADEUS_BASE_URL_ENDPOINT = "https://test.api.amadeus.com/v1/shopping/";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String PEXELS_API_KEY = BuildConfig.PexelsApiKey;
    public static final String AMADEUS_API_KEY = BuildConfig.AmadeusApiKey;
    protected static DataManager sDataManager;
    private Retrofit retrofit;
    private static final String HOTELS_SEARCH = "hotels";
    private static final String VACATION_SEARCH = "vacation";
    private static final String CITY_CODE = "cityCode";
    private List<Photo> photoList;
    private List<HotelTopRatedPhoto> hotelTopRatedPhotoList;
    private List<Data> dataList;
    private List<HotelSearchListener> hotelSearchListenerList;
    private List<HotelTopRatedSearchListener> hotelTopRatedSearchListenerList;
    private List<HotelOffersSearchListener> hotelOffersSearchListenerList;
    private ApiService apiService;
    private HotelOffersApi hotelOffersApi;

    DataManager(Retrofit retrofit) {
        this.retrofit = retrofit;
        hotelSearchListenerList = new ArrayList<>();
        hotelTopRatedSearchListenerList = new ArrayList<>();

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

    public void addHotelTopRatedSearchListener(HotelTopRatedSearchListener listener) {
        hotelTopRatedSearchListenerList.add(listener);
    }

    public void removeHotelTopRatedSearchListener(HotelTopRatedSearchListener listener) {
        hotelTopRatedSearchListenerList.remove(listener);
    }

    public void addHotelOffersSearchListener(HotelOffersSearchListener listener) {
        hotelOffersSearchListenerList.add(listener);
    }

    public void removeHotelOffersSearchListener(HotelOffersSearchListener listener) {
        hotelOffersSearchListenerList.remove(listener);
    }

    private void notifySearchListeners() {
        for (HotelSearchListener listener : hotelSearchListenerList) {
            listener.onHotelSearchFinished();
        }
    }

    private void notifyTopRatedSearchListeners() {
        for (HotelTopRatedSearchListener listener : hotelTopRatedSearchListenerList) {
            listener.onTopRatedSearchFinished();
        }
    }

    private void notifyHotelOffersListeners() {
        for (HotelOffersSearchListener listener : hotelOffersSearchListenerList) {
            listener.onHotelOffersFinished();
        }
    }

    public static DataManager get(Context context) {
        if (sDataManager == null) {
            Gson gson = new GsonBuilder()

                    // Inform the GsonBuilder that there is a custom deserializer by
                    // registering a type adapter, passing in the class it will
                    // output and the deserializer it should use to create the class.
                    .registerTypeAdapter(HotelPopularSearchResponse.class,
                            new PopularHotelListDeserializer())
                    .registerTypeAdapter(HotelTopRatedSearchResponse.class,
                            new TopRatedHotelListDeserializer())
                    .registerTypeAdapter(HotelOffersResponse.class,
                            new HotelOffersListDeserializer())
                    .create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(sRequestInterceptor)
                    //  .addInterceptor(sHotelOffersInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PEXELS_ENDPOINT)
                    //      .baseUrl(AMADEUS_BASE_URL_ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            sDataManager = new DataManager(retrofit);
            sDataManager.apiService = retrofit
                    .create(ApiService.class);
            sDataManager.hotelOffersApi = retrofit
                    .create((HotelOffersApi.class));
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

    private static Interceptor sHotelOffersInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url().newBuilder()
                    .build();

            Request request = chain.request().newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, AMADEUS_API_KEY)
                    .url(url)
                    .build();

            return chain.proceed(request);
        }
    };

    public void fetchHotelPopularSearch() {
        apiService.hotelsSearchPopular(HOTELS_SEARCH)
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
        apiService.hotelsSearchTopRated(VACATION_SEARCH)
                // Handles web request asynchronously
                .enqueue(new Callback<HotelTopRatedSearchResponse>() {
                    @Override
                    public void onResponse(Call<HotelTopRatedSearchResponse> call,
                                           retrofit2.Response<HotelTopRatedSearchResponse> response) {

                        hotelTopRatedPhotoList = response.body().getTopRatedPhotoList();
                        Timber.i("Sheila top rated photoList = %s",
                                hotelTopRatedPhotoList.toString());
                        notifyTopRatedSearchListeners();

                    }

                    @Override
                    public void onFailure(Call<HotelTopRatedSearchResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel search" + " ~ " + t);
                    }
                });
    }

    public void fetchHotelOffers() {
        hotelOffersApi.hotelOffersSearch(CITY_CODE)
                .enqueue(new Callback<HotelOffersResponse>() {
                    @Override
                    public void onResponse(Call<HotelOffersResponse> call,
                                           retrofit2.Response<HotelOffersResponse> response) {


                        dataList = response.body().getHotelOffersList();
                        Timber.i("Sheila hotelOffersList = %s", dataList.toString());
                        notifyHotelOffersListeners();
                    }

                    @Override
                    public void onFailure(Call<HotelOffersResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel Offers" + " ~ " + t);
                    }
                });
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public List<HotelTopRatedPhoto> getTopRatedPhotoList() {
        return hotelTopRatedPhotoList;
    }

    public List<HotelTopRatedPhoto> getHotelOffersList() {
        return hotelTopRatedPhotoList;
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
