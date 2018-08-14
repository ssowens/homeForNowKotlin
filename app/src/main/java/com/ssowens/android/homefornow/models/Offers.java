package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class Offers extends BaseObservable{

    private String id;
    private String rateCode;
    private RateFamilyEstimated rateFamilyEstimated;
    private Room room;
    private Description description;
    private Guests guests;
    private Price price;

    @SerializedName("self")
    public String hotelUrl;

    public String getId() {
        return id;
    }

    public String getRateCode() {
        return rateCode;
    }

    public RateFamilyEstimated getRateFamilyEstimated() {
        return rateFamilyEstimated;
    }

    public Room getRoom() {
        return room;
    }

    public Description getDescription() {
        return description;
    }

    public Guests getGuests() {
        return guests;
    }

    public Price getPrice() {
        return price;
    }

    public String getHotelUrl() {
        return hotelUrl;
    }

    @Override
    public String toString() {
        return "Offers{" +
                "id='" + id + '\'' +
                ", rateCode='" + rateCode + '\'' +
                ", rateFamilyEstimated=" + rateFamilyEstimated +
                ", room=" + room +
                ", description=" + description +
                ", guests=" + guests +
                ", price=" + price +
                ", hotelUrl='" + hotelUrl + '\'' +
                '}';
    }
}
