package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel extends BaseObservable {

    private String type;
    private String hotelId;
    private String chainCode;
    private String dupeId;
    private String name;
    private String cityCode;
    private Float latitude;
    private Float longitude;
    private List<Media> media;

    public String getType() {
        return type;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getChainCode() {
        return chainCode;
    }

    public String getDupeId() {
        return dupeId;
    }

    public String getName() {
        return name;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public List<Media> getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "type='" + type + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", chainCode='" + chainCode + '\'' +
                ", dupeId='" + dupeId + '\'' +
                ", name='" + name + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", media=" + media +
                '}';
    }

    /**
     * Created by Sheila Owens on 8/13/18.
     */
    public static class Media extends BaseObservable {

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
}
