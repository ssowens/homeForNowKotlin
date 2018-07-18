package models;

import com.google.gson.annotations.SerializedName;

public class Photos {

    private int width;
    private int height;

    @SerializedName("url")
    private String photoUrl;

    private String photographer;

    @SerializedName("src")
    private PictureSrc pictureSrc;

    public Photos(int width, int height, String photoUrl, String photographer, PictureSrc pictureSrc) {
        this.width = width;
        this.height = height;
        this.photoUrl = photoUrl;
        this.photographer = photographer;
        this.pictureSrc = pictureSrc;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getPhotographer() {
        return photographer;
    }

    public PictureSrc getPictureSrc() {
        return pictureSrc;
    }
}
