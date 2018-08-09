package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 7/23/18.
 */
public class HotelPopularSearchResponse {

    @SerializedName("photos")
    List<Photo> photoList;

    public List<Photo> getPhotoList() {
        return photoList;
    }

}
