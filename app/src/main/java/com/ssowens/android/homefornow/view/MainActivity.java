package com.ssowens.android.homefornow.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.ssowens.android.homefornow.BuildConfig;
import com.ssowens.android.homefornow.R;

import timber.log.Timber;

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
            return PhotoFragment.newInstance();
        } else {
            Toast.makeText(this, getString(R.string.no_internet_service),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
