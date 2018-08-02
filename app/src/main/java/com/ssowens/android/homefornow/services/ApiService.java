package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.HotelSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //https://api.pexels.com/v1/search?query=example+query&per_page=15&page=1
    // Popular
    @GET("search")
    Call<HotelSearchResponse> hotelsSearhPopular(@Query("query") String hotel);

    //https://api.pexels.com/v1/curated?per_page=15&page=1
    // Top Rated
    @GET("curated")
    Call<HotelSearchResponse> hotelsSearchTopRated(@Query("curated") String hotel);

}
