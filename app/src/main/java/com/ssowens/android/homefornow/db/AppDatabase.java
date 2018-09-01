package com.ssowens.android.homefornow.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ssowens.android.homefornow.R;

import timber.log.Timber;

/**
 * Created by Sheila Owens on 8/26/18.
 */
@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoritelist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d(String.valueOf(R.string.create_db_instance));
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Timber.d(String.valueOf(R.string.get_db_instance));
        return sInstance;
    }

    public abstract FavoriteDao favoriteDao();
}

