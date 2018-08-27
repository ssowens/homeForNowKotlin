package com.ssowens.android.homefornow.models;

import android.content.Context;

/**
 * Created by Sheila Owens on 8/11/18.
 */
public class TokenStore {

    private static final String TOKEN_KEY = "TokenStore.TokenKey";

    private static TokenStore sTokenStore;
    private String currentToken;

    public static TokenStore get(Context context) {
        if (sTokenStore == null) {
            sTokenStore = new TokenStore(context);
        }
        return sTokenStore;
    }

    private TokenStore(Context context) {
        Context appContext = context.getApplicationContext();
    }

    public String getAccessToken() {
        return currentToken;
    }

    public void setAccessToken(String accessToken) {
    }
}
