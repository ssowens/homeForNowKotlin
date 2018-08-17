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

    public void setType(String type) {
        this.type = type;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public void setDupeId(String dupeId) {
        this.dupeId = dupeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
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

        public void setHotelPhotoUrl(String hotelPhotoUrl) {
            this.hotelPhotoUrl = hotelPhotoUrl;
        }

        @BindingAdapter("uri")
        public static void loadImage(ImageView view, String hotelPhotoUrl) {
            Glide.with(view.getContext())
                    .load(hotelPhotoUrl)
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
