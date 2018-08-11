package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.AmadeusAccessTokenResponse;
import com.ssowens.android.homefornow.models.HotelOffersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_AUTHORIZATION_ENDPOINT;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_CLIENT_ID;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_CLIENT_SECRET;
import static com.ssowens.android.homefornow.utils.DataManager.AMADEUS_GRANT_TYPE;

/**
 * Created by Sheila Owens on 8/6/18.
 */
public interface HotelOffersApi {

    // Hotel Details
    @GET("hotel-offers")
    Call<HotelOffersResponse> hotelOffersSearch();


    @FormUrlEncoded
    @POST(AMADEUS_AUTHORIZATION_ENDPOINT)
    Call<AmadeusAccessTokenResponse> getAmadeusToken(@Field(AMADEUS_GRANT_TYPE) String grant_type,
                                                     @Field(AMADEUS_CLIENT_ID) String client_id,
                                                     @Field(AMADEUS_CLIENT_SECRET) String client_secret);
}
