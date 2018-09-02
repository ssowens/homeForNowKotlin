package com.ssowens.android.homefornow.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Sheila Owens on 8/26/18.
 */

@Entity(tableName = "favorite")
public class Favorite {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String hotelId;
    private String photeId;
    private String photographer;
    private String hotelName;
    private String photoUrl;
    private String guests;
    private String roomType;
    private String price;
    private String description;
    private String raters;
    private String bedType;
    private String beds;
    private boolean isFavorite;

    @Ignore
    public Favorite(@NonNull String hotelId,
                    String photeId,
                    String photographer, String hotelName,
                    String photoUrl,
                    String guests,
                    String roomType,
                    String price,
                    String description,
                    String raters,
                    String bedType,
                    String beds,
                    boolean isFavorite) {
        this.hotelId = hotelId;
        this.photeId = photeId;
        this.photographer = photographer;
        this.hotelName = hotelName;
        this.photoUrl = photoUrl;
        this.guests = guests;
        this.roomType = roomType;
        this.price = price;
        this.description = description;
        this.raters = raters;
        this.bedType = bedType;
        this.beds = beds;
        this.isFavorite = isFavorite;
    }

    public Favorite(@NonNull int id, @NonNull String hotelId,
                    String photeId,
                    String photographer, String hotelName,
                    String photoUrl,
                    String guests,
                    String roomType,
                    String price,
                    String description,
                    String raters,
                    String bedType,
                    String beds,
                    boolean isFavorite) {
        this.id = id;
        this.hotelId = hotelId;
        this.photeId = photeId;
        this.photographer = photographer;
        this.hotelName = hotelName;
        this.photoUrl = photoUrl;
        this.guests = guests;
        this.roomType = roomType;
        this.price = price;
        this.description = description;
        this.raters = raters;
        this.bedType = bedType;
        this.beds = beds;
        this.isFavorite = isFavorite;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(@NonNull String hotelId) {
        this.hotelId = hotelId;
    }

    public String getPhoteId() {
        return photeId;
    }

    public void setPhoteId(String photeId) {
        this.photeId = photeId;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}


