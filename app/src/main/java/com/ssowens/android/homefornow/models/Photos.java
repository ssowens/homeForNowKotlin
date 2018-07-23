package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class Photos extends BaseObservable {

    String imageUrl;

    private int width;
    private int height;

    @SerializedName("url")
    private String photoUrl;

    private String photographer;

    @SerializedName("src")
    private PictureSrc pictureSrc;

    public Photos(int width, int height, String photoUrl, String photographer, PictureSrc pictureSrc) {
        this.width = width;
        this.height = height;
        this.photoUrl = photoUrl;
        this.photographer = photographer;
        this.pictureSrc = pictureSrc;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public PictureSrc getPictureSrc() {
        return pictureSrc;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
