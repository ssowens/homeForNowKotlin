package com.ssowens.android.homefornow.models;

import android.text.TextUtils;

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
    public String guests;
    public String price;
    public String description;
    public String bed;
    public String bedType;

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
        return getHotel().getMedia().get(0).getHotelPhotoUrl();
    }


    public String getGuests() {
        if (!TextUtils.isEmpty(getOffers().get(0).getGuests().getAdults()))
            return getOffers().get(0).getGuests().getAdults() + " " + "guests";
        return "0";
    }

    public String getPrice() {
        String price = "$0.00";
        if (!TextUtils.isEmpty(getOffers().get(0).getPrice().getTotal())) {
            price = "$" + getOffers().get(0).getPrice().getTotal()
                    + " " + getOffers().get(0).getPrice()
                    .getCurrency();
        }
        return price;
    }

    public String getDescription() {
        return getOffers().get(0).getRoom().getDescription().getText();
    }


    public int getBed() {
        return getOffers().get(0).getRoom().getTypeEstimated().getBeds();
    }

    public String getBedType() {
        return getOffers().get(0).getRoom().getTypeEstimated().getBedType();
    }

    @Override
    public String toString() {
        return "HotelDetailData{" +
                "type='" + type + '\'' +
                ", hotel=" + hotel +
                ", available=" + available +
                ", offers=" + offers +
                ", imageUrl='" + imageUrl + '\'' +
                ", guests='" + guests + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", bed='" + bed + '\'' +
                ", bedType='" + bedType + '\'' +
                '}';
    }
}
