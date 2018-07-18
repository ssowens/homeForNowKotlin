package com.ssowens.android.homefornow.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ssowens.android.homefornow.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant();
    }
}
