package com.ssowens.android.homefornow.models;

/**
 * Created by Sheila Owens on 8/11/18.
 */
public class AmadeusAccessTokenResponse {
    private String type;
    private String username;
    private String application_name;
    private String client_id;
    private String token_type;
    public String access_token;
    private String expires_in;
    private String state;
    private String scope;

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getApplication_name() {
        return application_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getState() {
        return state;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "AmadeusAccessTokenResponse{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", application_name='" + application_name + '\'' +
                ", client_id='" + client_id + '\'' +
                ", token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", state='" + state + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
