package com.ssowens.android.homefornow.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Sheila Owens on 8/26/18.
 */
    @Database(entities =  {Favorites.class}, version = 1, exportSchema = false)

    public abstract class FavoritesDatabase extends RoomDatabase {
        public abstract FavoriteDao favoriteDao();
    }

