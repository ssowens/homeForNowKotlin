package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;

/**
 * Created by Sheila Owens on 8/1/18.
 */
public class HotelDetailActivity extends SingleFragmentActivity {

    public static final String ARG_HOTEL_ID = "HotelDetailActivity.HotelId";
    public static final String ARG_PHOTO_ID = "photoId";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            String hotelId = getIntent().getStringExtra(ARG_HOTEL_ID);
            String photoId = getIntent().getStringExtra(ARG_PHOTO_ID);
            return HotelDetailFragment.newInstance(hotelId, photoId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
