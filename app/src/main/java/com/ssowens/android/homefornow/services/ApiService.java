package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Popular Hotels
    @GET("search")
    Call<HotelPopularSearchResponse> hotelsSearchPopular(@Query("query") String hotel);

    // Top Rated Hotels
    @GET("search")
    Call<HotelTopRatedSearchResponse> hotelsSearchTopRated(@Query("query") String hotel);

}
