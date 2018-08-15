package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;

import java.util.List;

public class Data extends BaseObservable {

    private String type;
    private Hotel hotel;
    private boolean available;
    private List<Offers> offers;

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
        return offers;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type='" + type + '\'' +
                ", hotel=" + hotel +
                ", available=" + available +
                ", offers=" + offers +
                '}';
    }
}
