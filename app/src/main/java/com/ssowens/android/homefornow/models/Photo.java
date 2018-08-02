package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class Photo extends BaseObservable {

    private String id;
    private int width;
    private int height;

    @SerializedName("url")
    private String photoUrl;

    private String photographer;

    @SerializedName("src")
    private PictureSrc pictureSrc;

    public Photo(int width, int height, String photoUrl, String photographer, PictureSrc pictureSrc) {
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

    @Bindable
    public String getPhotoUrl() {
        return pictureSrc.getSmall();
    }

    public String getPhotographer() {
        return photographer;
    }

    public PictureSrc getPictureSrc() {
        return pictureSrc;
    }

    @BindingAdapter("photoUrl")
    public static void loadImage(ImageView view, String photoUrl) {
        Glide.with(view.getContext())
                .load(photoUrl)
                .into(view);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", photoUrl='" + photoUrl + '\'' +
                ", photographer='" + photographer + '\'' +
                ", pictureSrc=" + pictureSrc +
                '}';
    }
}
