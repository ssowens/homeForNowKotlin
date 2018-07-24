package com.ssowens.android.homefornow.remote;

import com.ssowens.android.homefornow.models.HotelSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // https://www.pexels.com/search/hotels/
    //https://api.pexels.com/v1/search?query=example+query&per_page=15&page=1
    @GET("search")
    Call<HotelSearchResponse> getImages(@Query("hotel") String hotel);

}
