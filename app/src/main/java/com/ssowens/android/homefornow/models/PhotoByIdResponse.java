package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 8/23/18.
 */
public class PhotoByIdResponse {

    @SerializedName("photos")
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }
}
