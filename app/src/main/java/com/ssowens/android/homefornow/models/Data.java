package com.ssowens.android.homefornow.models;

import java.util.List;

public class Data {
    private String type;
    private Hotel hotel;
    private boolean available;
    List<Offers> offersList;

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
}
