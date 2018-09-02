package com.ssowens.android.homefornow.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ssowens.android.homefornow.db.AppDatabase;
import com.ssowens.android.homefornow.db.Favorite;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Sheila Owens on 9/1/18.
 */
public class HotelDetailViewModel extends AndroidViewModel {

    private LiveData<List<Favorite>> favorites;

    public HotelDetailViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Timber.d("Actively retrieving the favorites from the Database");
        favorites = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<Favorite>> getFavorites() {
        return favorites;
    }
}
