package com.ssowens.android.homefornow.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sheila Owens on 8/11/18.
 */
public class TokenStore {

    private static final String TOKEN_KEY = "TokenStore.TokenKey";

    private static TokenStore sTokenStore;
    private SharedPreferences sharedPreferences;

    public static TokenStore get(Context context) {
        if (sTokenStore == null) {
            sTokenStore = new TokenStore(context);
        }
        return sTokenStore;
    }

    private TokenStore(Context context) {
        Context appContext = context.getApplicationContext();
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    public String getAccessToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit()
                .putString(TOKEN_KEY, accessToken)
                .apply();
    }
}
