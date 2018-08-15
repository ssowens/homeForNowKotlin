package com.ssowens.android.homefornow.models;

import java.util.Arrays;

/**
 * Created by Sheila Owens on 8/13/18.
 */
public class HotelDetailsData {

    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HotelDetailsData{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
