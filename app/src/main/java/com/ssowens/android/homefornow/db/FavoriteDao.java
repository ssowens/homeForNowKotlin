package com.ssowens.android.homefornow.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Sheila Owens on 8/26/18.
 */

@Dao
public interface FavoriteDao {

    @Insert
    void insertFavorite(Favorite favorite);

    @Query("SELECT * FROM favorite ORDER BY hotelName")
    LiveData<List<Favorite>> loadAllFavorites();

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<Favorite> loadFavoriteById(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void  updateFavorite (Favorite favorite);

    @Delete
    void deleteFavorite (Favorite favorite);

}
