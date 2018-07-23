package com.ssowens.android.homefornow.models;

import java.util.List;

public class MyImages {

    private int page;
    private int per_page;
    private int total_results;
    private String url;
    private String next_page;
    public List<Photos> photosList;

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public String getUrl() {
        return url;
    }

    public String getNext_page() {
        return next_page;
    }

    public List<Photos> getPhotosList() {
        return photosList;
    }
}
