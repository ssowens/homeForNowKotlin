package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 8/7/18.
 */
public class HotelTopRatedSearchResponse {

    @SerializedName("photos")
    List<HotelTopRatedPhoto> topRatedHotelphotoList;

    public List<HotelTopRatedPhoto> getTopRatedPhotoList() {
        return topRatedHotelphotoList;
    }
}
