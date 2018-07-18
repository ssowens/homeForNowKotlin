package com.ssowens.android.homefornow.remote;

import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

/**
 * Created by Sheila Owens on 7/17/18.
 */
public class Config {

    public static final URL BASE_URL;
    public static final String searchURL = "/hotel-offers";
    public static final String searchParameter = "?cityCode=PAR\\";

    static {
        URL url = null;
        try {
            url = new URL("https://test.api.amadeus.com/v1/shopping" + searchURL);
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Timber.e("Please check your internet connection.");
        }

        BASE_URL = url;
    }

}
