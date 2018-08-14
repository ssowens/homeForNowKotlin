package com.ssowens.android.homefornow.models;

import java.util.List;

public class Data {

    private String type;
    private Hotel hotel;
    private boolean available;
    private List<Offers> offersList;
    private List<Media> mediaList;

    public String getType() {
        return type;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<Offers> getOffersList() {
        return offersList;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type='" + type + '\'' +
                ", hotel=" + hotel +
                ", available=" + available +
                ", offersList=" + offersList +
                ", mediaList=" + mediaList +
                '}';
    }
}
