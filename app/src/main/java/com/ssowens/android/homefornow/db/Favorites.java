package com.ssowens.android.homefornow.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Sheila Owens on 8/26/18.
 */

@Entity
public class Favorites {

    @NonNull
    @PrimaryKey
    private String hotelId;
    private String hotelName;
    private String photoUrl;
    private String guests;
    private String roomType;
    private String price;
    private String description;
    private String raters;
    private String bedType;
    private String beds;

    @NonNull
    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(@NonNull String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String getGuests() {
        return guests;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public String getRoomType() {
        return roomType;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRaters(String raters) {
        this.raters = raters;
    }
    public String getRaters() {
        return raters;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
    public String getBedType() {
        return bedType;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }
    public String getBeds() {
        return beds;
    }

    public Favorites(@NonNull String hotelId,
                     String hotelName,
                     String photoUrl,
                     String guests,
                     String roomType,
                     String price,
                     String description,
                     String raters,
                     String bedType,
                     String beds) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.photoUrl = photoUrl;
        this.guests = guests;
        this.roomType = roomType;
        this.price = price;
        this.description = description;
        this.raters = raters;
        this.bedType = bedType;
        this.beds = beds;
    }
}


