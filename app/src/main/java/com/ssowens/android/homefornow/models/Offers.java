package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

public class Offers {

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
}
