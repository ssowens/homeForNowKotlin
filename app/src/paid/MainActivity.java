package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.R;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.EXTRA_HOTEL_RATING;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.POPULAR_HOTEL;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            int hotelRating = getIntent().getIntExtra(EXTRA_HOTEL_RATING, POPULAR_HOTEL);
            return PopularHotelFragment.newInstance(hotelRating);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return FavoritesFragment.newInstance("", "", isOnline());
        }

    }
}
