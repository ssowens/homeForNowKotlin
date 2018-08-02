package com.ssowens.android.homefornow.services;

import com.ssowens.android.homefornow.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

/**
 * Created by Sheila Owens on 7/17/18.
 */
public class Config {

    private static final URL BASE_URL;
    private static final String SEARCH_URL = "/hotel-offers";
    public static final String SEARCHPARAMETER = "?cityCode=PAR\\";
    public static final String AMADEUS_API_KEY = BuildConfig.AmadeusApiKey;
    public static final String AMADEUS_SECRET = BuildConfig.AmadeusSecret;

    static {
        URL url = null;
        try {
            url = new URL("https://test.api.amadeus.com/v1/shopping" + SEARCH_URL);
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Timber.e("Please check your internet connection.");
        }

        BASE_URL = url;
    }

}
