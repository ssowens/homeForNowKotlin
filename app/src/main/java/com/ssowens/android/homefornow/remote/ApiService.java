package com.ssowens.android.homefornow.remote;

import java.util.List;

import models.MyImages;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/json")
    Call<List<MyImages>> getImages();
}
