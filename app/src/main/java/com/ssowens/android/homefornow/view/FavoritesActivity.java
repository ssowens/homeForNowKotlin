package com.ssowens.android.homefornow.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;

public class FavoritesActivity extends SingleFragmentActivity {
    public static final String EXTRA_HOTEL_ID = "hotelId";

    @Override
    protected Fragment createFragment() {

        String hotelId;

        if (isOnline()) {
            hotelId = (String) getIntent().getSerializableExtra(EXTRA_HOTEL_ID);
            return FavoritesFragment.newInstance(hotelId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
