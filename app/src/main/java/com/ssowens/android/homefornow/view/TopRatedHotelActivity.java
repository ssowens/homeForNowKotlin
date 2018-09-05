package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ssowens.android.homefornow.R;

import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.EXTRA_HOTEL_TYPE;
import static com.ssowens.android.homefornow.view.TopRatedHotelFragment.POPULAR_HOTEL;

/**
 * Created by Sheila Owens on 8/8/18.
 */
public class TopRatedHotelActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        if (isOnline()) {
            int hotel_rating = getIntent().getIntExtra(EXTRA_HOTEL_TYPE, POPULAR_HOTEL);
            return TopRatedHotelFragment.newInstance(hotel_rating);
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
