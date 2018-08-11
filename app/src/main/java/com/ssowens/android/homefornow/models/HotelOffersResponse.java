package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 8/6/18.
 */
public class HotelOffersResponse {

    @SerializedName("data")
    List<HotelDetailsData> hotelOffersList;

    public List<HotelDetailsData> getHotelOffersList() {
        return hotelOffersList;
    }
}
