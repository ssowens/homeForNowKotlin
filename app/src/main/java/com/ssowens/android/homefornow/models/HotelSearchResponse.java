package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 7/23/18.
 */
public class HotelSearchResponse {

    @SerializedName("photos")
    List<Photos> photosList;

    public List<Photos> getPhotosList() {
        return photosList;
    }

}
