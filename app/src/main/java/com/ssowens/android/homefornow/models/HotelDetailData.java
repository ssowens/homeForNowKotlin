package com.ssowens.android.homefornow.models;

import java.util.List;

/**
 * Created by Sheila Owens on 8/13/18.
 */
public class HotelDetailData {

    private String type;
    private Hotel hotel;
    private boolean available;
    private List<Offers> offers;
    public String imageUrl;

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

    public List<Offers> getOffers() {
        return offers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return offers.get(0).getDescription().getText();
    }

    @Override
    public String toString() {
        return "HotelDetailData{" +
                "type='" + type + '\'' +
                ", hotel=" + hotel +
                ", available=" + available +
                ", offers=" + offers +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
