package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.models.HotelPopularSearchResponse;
import com.ssowens.android.homefornow.models.HotelTopRatedSearchResponse;
import com.ssowens.android.homefornow.models.Photo;
import com.ssowens.android.homefornow.models.PhotoByIdResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Popular Hotels
    @GET("search")
    Call<HotelPopularSearchResponse> hotelsSearchPopular(@Query("query") String hotel);

    // Top Rated Hotels
    @GET("search")
    Call<HotelTopRatedSearchResponse> hotelsSearchTopRated(@Query("query") String hotel);

    // Hotel By Id
    // https://api.pexels.com/v1/photos/:id
    @GET("https://api.pexels.com/v1/photos/{id}")
    Call<Photo> photoById(@Path("id") String photoId);
}
