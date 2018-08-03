package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelTopRatedResponse {

    @SerializedName("photos")
    List<Photo> photoList;

    public List<Photo> getHotelTopRatedList() {
        return photoList;
    }

}
