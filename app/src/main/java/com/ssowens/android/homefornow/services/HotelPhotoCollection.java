package com.ssowens.android.homefornow.services;

import android.content.Context;

import com.ssowens.android.homefornow.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 7/25/18.
 */
public class HotelPhotoCollection {
    private static HotelPhotoCollection sHotelPhotoCollection;
    private List<Photo> hotelPhotoList;

    public static HotelPhotoCollection get(Context context) {
        if (sHotelPhotoCollection == null) {
            sHotelPhotoCollection = new HotelPhotoCollection(context);
        }
        return sHotelPhotoCollection;
    }

    private HotelPhotoCollection(Context context) {
        hotelPhotoList = new ArrayList<>();
    }

    public List<Photo> getHotelPhotos() {
        return hotelPhotoList;
    }

    public void addHotelPhotoColleciton(Photo photo) {
        hotelPhotoList.add(photo);
    }

    public void addListRecipeCollection(List<Photo> recipeList) {
        hotelPhotoList.addAll(recipeList);
    }

}
