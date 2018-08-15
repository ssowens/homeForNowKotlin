package com.ssowens.android.homefornow.utils;

import android.net.Uri;

/**
 * Created by Sheila Owens on 8/11/18.
 */
public class OauthUriHelper {
    private static final String ACCESS_TOKEN_PARAM = "access_token=";
    private Uri mOauthUri;

    public OauthUriHelper(String oauthUri) {
        mOauthUri = Uri.parse(oauthUri);
    }

    public String getAccessToken() {
        String uriFragment = mOauthUri.getFragment();
        if (uriFragment.contains(ACCESS_TOKEN_PARAM)) {
            return uriFragment.substring(ACCESS_TOKEN_PARAM.length());
        }
        return null;
    }

    public boolean isAuthorized() {
        return getAccessToken() != null;
    }
}
