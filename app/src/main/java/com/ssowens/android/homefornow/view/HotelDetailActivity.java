package com.ssowens.android.homefornow.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;

/**
 * Created by Sheila Owens on 8/1/18.
 */
public class HotelDetailActivity extends SingleFragmentActivity {

    public static final String ARG_HOTEL_ID = "HotelDetailActivity.HotelId";

    public static Intent newIntent(Context context, String hotelId) {
        Intent intent = new Intent(context, HotelDetailActivity.class);
        intent.putExtra(ARG_HOTEL_ID, hotelId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            String hotelId = getIntent().getStringExtra(ARG_HOTEL_ID);
            return HotelDetailFragment.newInstance(hotelId);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String hotelId = getIntent().getStringExtra(ARG_HOTEL_ID);
        setContentView(R.layout.activity_hotel_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, HotelDetailFragment.newInstance(hotelId))
                    .commit();
        }
    }
}
