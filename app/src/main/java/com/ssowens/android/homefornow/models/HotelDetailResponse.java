package com.ssowens.android.homefornow.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheila Owens on 8/19/18.
 */
public class HotelDetailResponse {

    @SerializedName("data")
    HotelDetailData hotelDetails;

    public HotelDetailData getHotelDetails() {
        return hotelDetails;
    }
}
