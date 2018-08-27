package com.ssowens.android.homefornow.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Sheila Owens on 8/26/18.
 */

@Dao
public interface FavoriteDao {

    @Insert
    void insertSingleHotel(Favorites favorites);

    @Insert
    void insertManyHotels (List<Favorites> favoritesList);

//    @Query("SELECT * FROM Favorites WHERE hotelId = :hotelId")
//    LiveData<List<Favorites>> fetchOneHotelByHotelId (int hotelId);

//    @Query("SELECT * FROM Favorites WHERE hotelId = '*'")
//    LiveData<List<Favorites>> fetchFavoriteHotels;

    @Update
    void  updateFavorite (Favorites favorites);

    @Delete
    void deleteFavorite (Favorites favorites);

}
