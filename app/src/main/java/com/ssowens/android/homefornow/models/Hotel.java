package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;

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
}
