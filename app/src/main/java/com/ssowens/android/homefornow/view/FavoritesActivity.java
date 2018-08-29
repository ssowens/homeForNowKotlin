package com.ssowens.android.homefornow.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;

import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_HOTEL_ID;
import static com.ssowens.android.homefornow.view.HotelDetailActivity.ARG_PHOTO_ID;

public class FavoritesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        if (isOnline()) {
            String hotelId = getIntent().getStringExtra(ARG_HOTEL_ID);
            String photoId = getIntent().getStringExtra(ARG_PHOTO_ID);
            return FavoritesFragment.newInstance(hotelId, photoId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
