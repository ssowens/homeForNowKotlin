package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.AmadeusAccessTokenResponse;
import com.ssowens.android.homefornow.models.HotelDetailResponse;
import com.ssowens.android.homefornow.models.HotelOffersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_AUTHORIZATION_ENDPOINT;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_CLIENT_ID;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_CLIENT_SECRET;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_GRANT_TYPE;

/**
 * Created by Sheila Owens on 8/6/18.
 */
public interface HotelOffersApi {

    // Hotel Details
    @GET("v1/shopping/hotel-offers")
    Call<HotelOffersResponse> hotelOffersSearch(@Header("Authorization") String authorization,
                                                @Query("cityCode") String cityCode,
                                                @Query("radius") String radius,
                                                @Query("radiusUnit") String radiusUnit,
                                                @Query("includeClosed") String includeClosed,
                                                @Query("bestRateOnly") String bestRateOnly,
                                                @Query("view") String view,
                                                @Query("sort") String sort,
                                                @Query("ratings") int rating);


    @FormUrlEncoded
    @POST(AMADEUS_AUTHORIZATION_ENDPOINT)
    Call<AmadeusAccessTokenResponse> getAmadeusToken(@Field(AMADEUS_GRANT_TYPE) String grant_type,
                                                     @Field(AMADEUS_CLIENT_ID) String client_id,
                                                     @Field(AMADEUS_CLIENT_SECRET) String client_secret);

    @GET("v1/shopping/hotels/{hotelId}/hotel-offers")
    Call<HotelDetailResponse> hotelOffersSearchById(@Header("Authorization") String authorization,
                                                    @Path("hotelId") String hotelId,
                                                    @Query("view") String view);
}
