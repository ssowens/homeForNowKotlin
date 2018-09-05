package com.ssowens.android.homefornow.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.listeners.AccessTokenListener;
import com.ssowens.android.homefornow.listeners.HotelDetailListener;
import com.ssowens.android.homefornow.listeners.HotelImageListener;
import com.ssowens.android.homefornow.listeners.HotelOffersSearchListener;
import com.ssowens.android.homefornow.listeners.PhotoByIdListener;
import com.ssowens.android.homefornow.models.AmadeusAccessTokenResponse;
import com.ssowens.android.homefornow.models.Data;
import com.ssowens.android.homefornow.models.Hotel;
import com.ssowens.android.homefornow.models.HotelDetailData;
import com.ssowens.android.homefornow.models.HotelDetailResponse;
import com.ssowens.android.homefornow.models.HotelOffersResponse;
import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;
import com.ssowens.android.homefornow.models.Offers;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.models.PhotoByIdResponse;
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


    // ENDPOINTS FOR API
    private static final String PEXELS_ENDPOINT = "https://api.pexels.com/v1/";
    private static final String AMADEUS_ENDPOINT = "https://test.api.amadeus" +
            ".com/v1/shopping/";
    public static final String AMADEUS_AUTHORIZATION_ENDPOINT = "v1/security/oauth2/token/";
    public static final String AMADEUS_BASE_URL = "https://test.api.amadeus.com/";

    // By HOTEL ID
    // https://test.api.amadeus.com/v1/shopping/hotels/DTLAX213/hotel-offers?view=FULL

    public static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_AUTHORIZATION_BEARER = "Authorization: Bearer";
    private static final String HEADER_BEARER = "Bearer ";
    private static final int HOTEL_RATING = 3;
    private static final String HOTEL_VIEW = "FULL";

    // KEYS FOR API
    private static final String PEXELS_API_KEY = BuildConfig.PexelsApiKey;
    private static final String AMADEUS_API_KEY = BuildConfig.AmadeusApiKey;
    private static final String AMADEUS_SECRET = BuildConfig.AmadeusSecret;
    public static final String AMADEUS_GRANT_TYPE = "grant_type";
    public static final String AMADEUS_CLIENT_ID = "client_id";
    public static final String AMADEUS_CLIENT_SECRET = "client_secret";
    private static final String AMADEUS_CLIENT_CREDENTIALS = "client_credentials";
    public static final String AMADEUS_ACCESS_TOKEN = "access_token";


    protected static DataManager sDataManager;
    private Retrofit retrofit;
    private Retrofit hotelOffersRetrofit;
    private final Retrofit accessTokenRetrofit;
    private static final String HOTELS_SEARCH = "hotels";
    private static final String VACATION_SEARCH = "vacation";
    private List<Photo> photoList;
    private List<Photo> hotelImageList;
    private List<Photo> myPhotos;

    private List<Offers> hotelTopRatedHotelsList;
    private List<Data> dataList;
    private List<Data> dataByIdList;
    private HotelDetailData hotelDetail;


    private Photo photo;
    public Data data;
    private AmadeusAccessTokenResponse amadeusToken;

    private String tokenString;

    // Listeners List
    private List<HotelImageListener> hotelImageListenerList;
    private List<HotelOffersSearchListener> hotelOffersSearchListenerList;
    private List<AccessTokenListener> accessTokenListenerList;
    private List<HotelDetailListener> hotelDetailListenerList;
    private List<PhotoByIdListener> photoByIdListenerList;

    List<Hotel> hotelList = new ArrayList<>();
    List<Hotel> hotelListById = new ArrayList<>();

    private ApiService apiService;
    private HotelOffersApi hotelOffersApi;
    private static TokenStore sTokenStore;
    public String accessToken;
    public boolean isTokenAvail;

    DataManager(TokenStore tokenStore,
                Retrofit retrofit,
                Retrofit hotelOffersRetrofit,
                Retrofit accessTokenRetrofit) {
        sTokenStore = tokenStore;
        this.retrofit = retrofit;
        this.hotelOffersRetrofit = hotelOffersRetrofit;
        this.accessTokenRetrofit = accessTokenRetrofit;
        hotelImageListenerList = new ArrayList<>();
        hotelOffersSearchListenerList = new ArrayList<>();
        accessTokenListenerList = new ArrayList<>();
        hotelDetailListenerList = new ArrayList<>();
        photoByIdListenerList = new ArrayList<>();
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
                    .registerTypeAdapter(PhotoByIdResponse.class,
                            new PhotoListDeserializer())
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
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
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
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .build();

            // AMADEUS
            Retrofit accessTokenRetrofit = new Retrofit.Builder()
                    .baseUrl(AMADEUS_BASE_URL)
                    .client(accessTokenClient)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            //  TokenStore tokenStore = TokenStore.get(context);
            //sAccessToken = tokenStore.getAccessToken();

            // TODO Need to use a singleton
            TokenStore tokenStore = null;

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
                            notifyHotelImageListeners();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelPopularSearchResponse> call, Throwable t) {
                        Timber.e("** Failed to fetch hotel search" + " ~ " + t);
                    }
                });
    }

    public void fetchPhotosById(String photoId) {
        apiService.photoById(photoId)
                // Handles web request asynchronously
                .enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call,
                                           retrofit2.Response<Photo> response) {
                        if (response.body() != null) {
                            photo = response.body();
                            setPhoto(photo);
                            notifyPhotoByIdListeners();
                        }
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Timber.e(t, "Failed to fetch hotel by Id ~ ");
                    }
                });
    }

    public void fetchHotelPhotos() {
        apiService.hotelsSearchPopular(HOTELS_SEARCH)
                // Handles web request asynchronously
                .enqueue(new Callback<HotelPopularSearchResponse>() {
                    @Override
                    public void onResponse(Call<HotelPopularSearchResponse> call,
                                           retrofit2.Response<HotelPopularSearchResponse> response) {

                        if (response.body() != null) {
                            photoList = response.body().getPhotoList();
                            setupHotelImages(response.body().getPhotoList());
                            notifyHotelImageListeners();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelPopularSearchResponse> call, Throwable t) {
                        Timber.e("Failed to fetch hotel photos" + " ~ " + t);
                    }
                });
    }

    public void fetchHotelOffers(int hotelRating) {
        hotelOffersApi.hotelOffersSearch(getTokenString(), "LAX", "5",
                "KM", "false",
                "true", HOTEL_VIEW, "NONE", hotelRating)
                .enqueue(new Callback<HotelOffersResponse>() {
                    @Override
                    public void onResponse(Call<HotelOffersResponse> call,
                                           retrofit2.Response<HotelOffersResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            dataList = response.body().getHotelOffersList();

                            convertData(dataList);
                            notifyHotelOffersListeners();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelOffersResponse> call, Throwable t) {
                        Timber.e("Failed to fetch Hotel Offers" + " ~ " + t);
                    }
                });
    }

    public void getToken(final Callback callbackSuccess) {
        Call<AmadeusAccessTokenResponse> tokenCall =
                hotelOffersApi.getAmadeusToken(AMADEUS_CLIENT_CREDENTIALS,
                        AMADEUS_API_KEY, AMADEUS_SECRET);

        tokenCall.enqueue(callbackSuccess);
    }

    public void fetchAccessToken() {
        getToken(new Callback<AmadeusAccessTokenResponse>() {
            @Override
            public void onResponse(Call<AmadeusAccessTokenResponse> tokenCall,
                                   retrofit2.Response<AmadeusAccessTokenResponse>
                                           responseToken) {
                if (responseToken.isSuccessful() && responseToken.body() != null) {
                    amadeusToken = responseToken.body();
                    String tokenString = HEADER_BEARER + amadeusToken.getAccess_token();
                    amadeusToken.setAccess_token(tokenString);
                    setTokenString(tokenString);
                    notifyAccessTokenListeners();
                }
            }

            @Override
            public void onFailure(Call<AmadeusAccessTokenResponse> call, Throwable t) {
                Timber.e(t, " ~ Failed to fetch token");
            }
        });
    }

    public void fetchHotelOffersById(final String hotelId) {
        getToken(new Callback<AmadeusAccessTokenResponse>() {
            @Override
            public void onResponse(Call<AmadeusAccessTokenResponse> tokenCall,
                                   retrofit2.Response<AmadeusAccessTokenResponse>
                                           responseToken) {
                if (responseToken.isSuccessful() && responseToken.body() != null) {
                    AmadeusAccessTokenResponse token = responseToken.body();
                    String tokenString = HEADER_BEARER + token.getAccess_token();
                    token.setAccess_token(token.getAccess_token());

                    hotelOffersApi.hotelOffersSearchById(tokenString, hotelId, HOTEL_VIEW)
                            .enqueue(new Callback<HotelDetailResponse>() {
                                @Override
                                public void onResponse(Call<HotelDetailResponse> call,
                                                       retrofit2.Response<HotelDetailResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        hotelDetail = response.body().getHotelDetails();
                                        convertDataById(hotelDetail);
                                        notifyHotelDetailListeners();
                                    }
                                }

                                @Override
                                public void onFailure(Call<HotelDetailResponse> call, Throwable t) {
                                    Timber.e("Failed to fetch Hotel Offers By Id" + " ~ " + t);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<AmadeusAccessTokenResponse> call, Throwable t) {
                Timber.e(t, " ~ Failed to fetch the token");
            }
        });
    }

    private void setupHotelImages(List<Photo> photoList) {
        if (hotelImageList != null) {
            hotelImageList.clear();
        }
        hotelImageList = photoList;
    }

    private void convertDataById(HotelDetailData dataDetail) {
        hotelDetail = new HotelDetailData();
        if (dataDetail != null) {
            hotelDetail = dataDetail;
        }
    }

    private void convertData(List<Data> myData) {
        for (int iter = 0; iter < myData.size(); iter++) {
            hotelList.add(myData.get(iter).getHotel());
        }
    }


    public List<Photo> getPhotoList() {
        return photoList;
    }

    public List<Hotel> getTopRatedHotelsList() {
        return hotelList;
    }

    public HotelDetailData getHotelDetailData() {
        return hotelDetail;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photo.setPhotoUrl(photoUrl);
    }

    public String getAccessToken() {
        return amadeusToken.getAccess_token();
    }

    public Photo getHotelPhoto(String photoId) {
        for (Photo photo : photoList) {
            if (photo.getId().equals(photoId)) {
                return photo;
            }
        }
        return null;
    }

    public void addHotelImageListener(HotelImageListener listener) {
        hotelImageListenerList.add(listener);
    }

    public void removeHotelImageListener(HotelImageListener listener) {
        hotelImageListenerList.remove(listener);
    }

    public void addHotelOffersSearchListener(HotelOffersSearchListener listener) {
        hotelOffersSearchListenerList.add(listener);
    }

    public void removeHotelOffersSearchListener(HotelOffersSearchListener listener) {
        hotelOffersSearchListenerList.remove(listener);
    }

    private void notifyHotelImageListeners() {
        for (HotelImageListener listener : hotelImageListenerList) {
            listener.onHotelImageFinished();
        }
    }

    private void notifyPhotoByIdListeners() {
        for (PhotoByIdListener listener : photoByIdListenerList) {
            listener.onPhotoByIdFinished();
        }
    }

    private void notifyHotelOffersListeners() {
        for (HotelOffersSearchListener listener : hotelOffersSearchListenerList) {
            listener.onHotelOffersFinished();
        }
    }

    private void notifyAccessTokenListeners() {
        for (AccessTokenListener listener : accessTokenListenerList) {
            listener.onAccessTokenFinished();
        }
    }

    public void addAccessTokenListener(AccessTokenListener listener) {
        accessTokenListenerList.add(listener);
    }

    public void removeAccessTokenListener(AccessTokenListener listener) {
        accessTokenListenerList.remove(listener);
    }

    public void addPhotoByIdListener(PhotoByIdListener listener) {
        photoByIdListenerList.add(listener);
    }

    public void removePhotoByIdListener(PhotoByIdListener listener) {
        photoByIdListenerList.remove(listener);
    }

    public void addHotelDetailListener(HotelDetailListener listener) {
        hotelDetailListenerList.add(listener);
    }

    public void removeHotelDetailListener(HotelDetailListener listener) {
        hotelDetailListenerList.remove(listener);
    }

    private void notifyHotelDetailListeners() {
        for (HotelDetailListener listener : hotelDetailListenerList) {
            listener.onHotelDetailFinished();
        }
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }
}
