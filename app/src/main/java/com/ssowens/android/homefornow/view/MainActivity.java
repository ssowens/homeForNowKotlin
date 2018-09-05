package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.R;

import timber.log.Timber;

import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.EXTRA_HOTEL_TYPE;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.POPULAR_HOTEL;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        MobileAds.initialize(this, "ca-app-pub-3983610797900419~4189860710");
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            int hotel_rating = getIntent().getIntExtra(EXTRA_HOTEL_TYPE, POPULAR_HOTEL);
            return TopRatedHotelFragment.newInstance(hotel_rating);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return FavoritesFragment.newInstance("", "", isOnline());
        }

    }
}
