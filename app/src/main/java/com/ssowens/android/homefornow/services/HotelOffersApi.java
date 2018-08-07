package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.HotelOffersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Sheila Owens on 8/6/18.
 */
public interface HotelOffersApi {

    @GET("hotel-offers")
    Call<HotelOffersResponse> hotelOffersSearch(@Query("cityCode") String cityCodeString);
}
