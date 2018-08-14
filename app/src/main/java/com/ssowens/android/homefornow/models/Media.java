package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheila Owens on 8/13/18.
 */
public class Media extends BaseObservable {

    @SerializedName("uri")
    private String hotelPhotoUrl;

    public String getHotelPhotoUrl() {
        return hotelPhotoUrl;
    }

    public static void loadImage(ImageView view, String photoUrl) {
       Glide.with(view.getContext())
               .load(photoUrl)
               .into(view);
   }

    @Override
    public String toString() {
        return "Media{" +
                "hotelPhotoUrl='" + hotelPhotoUrl + '\'' +
                '}';
    }
}
