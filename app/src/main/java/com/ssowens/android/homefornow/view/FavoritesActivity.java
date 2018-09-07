package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;

public class FavoritesActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {

        String hotelId = getIntent().getStringExtra(ARG_HOTEL_ID);
        String photoId = getIntent().getStringExtra(ARG_PHOTO_ID);
        boolean isOffline = isOnline();
        return FavoritesFragment.newInstance(hotelId, photoId, isOffline);
    }
}
