package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.listeners.AccessTokenListener;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.listeners.HotelSearchListener;
import com.ssowens.android.homefornow.listeners.HotelTopRatedSearchListener;
import com.ssowens.android.homefornow.models.AmadeusAccessTokenResponse;
import com.ssowens.android.homefornow.models.Data;
import com.ssowens.android.homefornow.models.HotelOffersResponse;
import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedPhoto;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.models.TokenStore;
import com.ssowens.android.homefornow.services.ApiService;
import com.ssowens.android.homefornow.services.HotelOffersApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    // "https://test.api.amadeus.com/v1/shopping/hotel-offers?cityCode=PAR" -H "Authorization: Bearer ${token}" -k -o hotel_search_curl.json
    private static final String AMADEUS_ENDPOINT = "https://test.api.amadeus" +
            ".com/v1/shopping/";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String AMADEUS_BASE_URL = "https://test.api.amadeus.com/";
    private static final String HEADER_AUTHORIZATION_BEARER = "Authorization: Bearer";
    private static final String HEADER_BEARER = "Bearer ";
    public static final String PEXELS_API_KEY = BuildConfig.PexelsApiKey;
    public static final String AMADEUS_API_KEY = BuildConfig.AmadeusApiKey;
    public static final String AMADEUS_SECRET = BuildConfig.AmadeusSecret;
    public static final String AMADEUS_GRANT_TYPE = "grant_type";
    public static final String AMADEUS_CLIENT_ID = "client_id";
    public static final String AMADEUS_CLIENT_SECRET = "client_secret";
    public static final String AMADEUS_CLIENT_CREDENTIALS = "client_credentials";
    public static final String AMADEUS_ACCESS_TOKEN = "access_token";
    public static final String AMADEUS_AUTHORIZATION_ENDPOINT = "v1/security/oauth2/token/";
    protected static DataManager sDataManager;
    private Retrofit retrofit;
    private Retrofit hotelOffersRetrofit;
    private final Retrofit accessTokenRetrofit;
    private static final String HOTELS_SEARCH = "hotels";
    private static final String VACATION_SEARCH = "vacation";
    private List<Photo> photoList;
    private List<HotelTopRatedPhoto> hotelTopRatedPhotoList;
    private List<Data> dataList;
    public Data data;
    public AmadeusAccessTokenResponse amadeusAccessToken;

    // Listeners List
    private List<HotelSearchListener> hotelSearchListenerList;
    private List<HotelTopRatedSearchListener> hotelTopRatedSearchListenerList;
    private List<HotelOffersSearchListener> hotelOffersSearchListenerList;
    private List<AccessTokenListener> accessTokenListenerList;

    private ApiService apiService;
    private HotelOffersApi hotelOffersApi;
    private static TokenStore sTokenStore;
    public String accessToken;

    DataManager(TokenStore tokenStore,
                Retrofit retrofit,
                Retrofit hotelOffersRetrofit,
                Retrofit accessTokenRetrofit) {
        sTokenStore = tokenStore;
        this.retrofit = retrofit;
        this.hotelOffersRetrofit = hotelOffersRetrofit;
        this.accessTokenRetrofit = accessTokenRetrofit;
        hotelSearchListenerList = new ArrayList<>();
        hotelTopRatedSearchListenerList = new ArrayList<>();
        hotelOffersSearchListenerList = new ArrayList<>();
        accessTokenListenerList = new ArrayList<>();

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
                    .registerTypeAdapter(AmadeusAccessTokenResponse.class,
                            new AccessTokenDeserializer())
                    .create();


            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // TODO Make one OkHttpClient call, break it out like GsonConverterFactory

            // PEXELS
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(sRequestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // PEXELS
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PEXELS_ENDPOINT)
                    .client(client)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            // AMADEUS
            OkHttpClient hotelOffersClient = new OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .addInterceptor(sHotelOffersInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            // AMADEUS
            Retrofit hotelOffersRetrofit = new Retrofit.Builder()
                    .baseUrl(AMADEUS_BASE_URL)
                    .client(hotelOffersClient)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            // AMADEUS
            OkHttpClient accessTokenClient = new OkHttpClient.Builder()
                    .addInterceptor(sAccessTokenInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .build();

            // AMADEUS
            Retrofit accessTokenRetrofit = new Retrofit.Builder()
                    .baseUrl(AMADEUS_BASE_URL)
                    .client(accessTokenClient)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            TokenStore tokenStore = TokenStore.get(context);
            //sAccessToken = tokenStore.getAccessToken();

            sDataManager = new DataManager(tokenStore, retrofit, hotelOffersRetrofit,
                    accessTokenRetrofit);
            sDataManager.apiService = retrofit
                    .create(ApiService.class);
            sDataManager.hotelOffersApi = hotelOffersRetrofit
                    .create((HotelOffersApi.class));
            sDataManager.hotelOffersApi = accessTokenRetrofit
                    .create(HotelOffersApi.class);
        }
        return sDataManager;
    }

    DataManager() {
        throw new InstantiationError("Default constructor called for singleton");
    }


    // The Interceptor is used to dynamically add parameters to the
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
            Request request = chain.request();
            HttpUrl url = chain.request().url().newBuilder()
                    .build();

            request = request.newBuilder()
                    .url(url)
                    .build();

            return chain.proceed(request);
        }
    };

    private static Interceptor sAccessTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url().newBuilder()
                    .build();

            Request request = chain.request().newBuilder()
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

                        if (response.body() != null) {
                            photoList = response.body().getPhotoList();
                            Timber.i("Sheila popular photoList = %s", photoList.toString());
                            notifySearchListeners();
                        }

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
                        if (response.body() != null) {
                            hotelTopRatedPhotoList = response.body().getTopRatedPhotoList();
                            Timber.i("Sheila top rated photoList = %s",
                                    hotelTopRatedPhotoList.toString());
                            notifyTopRatedSearchListeners();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelTopRatedSearchResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel search" + " ~ " + t);
                    }
                });
    }

    public void fetchHotelOffers() {
        getToken(new Callback<AmadeusAccessTokenResponse>() {
            @Override
            public void onResponse(Call<AmadeusAccessTokenResponse> tokenCall,
                                   retrofit2.Response<AmadeusAccessTokenResponse>
                                           responseToken) {
                if (responseToken.isSuccessful() && responseToken.body() != null) {
                    AmadeusAccessTokenResponse token = responseToken.body();
                    String tokenString = HEADER_BEARER + token.getAccess_token();
                    Timber.i("Sheila Got the Token %s", token.getAccess_token());

                    hotelOffersApi.hotelOffersSearch(tokenString, "LAX", "5",
                            "KM", "false",
                            "true", "FULL", "NONE")
                            .enqueue(new Callback<HotelOffersResponse>() {
                                @Override
                                public void onResponse(Call<HotelOffersResponse> call,
                                                       retrofit2.Response<HotelOffersResponse> response) {
                                    Timber.i("Sheila fetchHotelOffers ~ %s", response.toString());
                                    if (response.isSuccessful() && response.body() != null) {
                                        dataList = response.body().getHotelOffersList();
                                        //List<Hotel> hotelList = dataList
                                        Timber.i("Sheila hotelOffersList = %s", dataList.toString());
                                        notifyHotelOffersListeners();
                                    }
                                }

                                @Override
                                public void onFailure(Call<HotelOffersResponse> call, Throwable t) {
                                    Timber.e("Failed to fetch Hotel Offers" + " ~ " + t);
                                }
                            });

                }
            }

            @Override
            public void onFailure(Call<AmadeusAccessTokenResponse> call, Throwable t) {
                Timber.e(t, " ~ Failed to fetch token");
            }
        });
    }

    public void getToken(final Callback callbackSuccess) {
        Call<AmadeusAccessTokenResponse> tokenCall =
                hotelOffersApi.getAmadeusToken(AMADEUS_CLIENT_CREDENTIALS,
                        AMADEUS_API_KEY, AMADEUS_SECRET);

        tokenCall.enqueue(callbackSuccess);
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public List<HotelTopRatedPhoto> getTopRatedPhotoList() {
        return hotelTopRatedPhotoList;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public String getAccessToken() {
        return amadeusAccessToken.getAccess_token();
    }

    public Photo getHotelPhoto(String photoId) {
        for (Photo photo : photoList) {
            if (photo.getId().equals(photoId)) {
                return photo;
            }
        }
        return null;
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

    public void addAccessTokenListener(AccessTokenListener listener) {
        accessTokenListenerList.add(listener);
    }

    public void removeAccessTokenListener(AccessTokenListener listener) {
        accessTokenListenerList.remove(listener);
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

}
