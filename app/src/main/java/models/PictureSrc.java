package models;

class PictureSrc {
    private String original;
    private String large;
    private String large2x;
    private String medium;
    private String small;
    private String portrait;
    private String landscape;
    private String tiny;

    public PictureSrc(String original, String large, String large2x, String medium, String small,
                      String portrait, String landscape, String tiny) {
        this.original = original;
        this.large = large;
        this.large2x = large2x;
        this.medium = medium;
        this.small = small;
        this.portrait = portrait;
        this.landscape = landscape;
        this.tiny = tiny;
    }


    // The size of the original image is given with the attributes width and height.
    public String getOriginal() {
        return original;
    }

    // 	This image has a maximum width of 940px and a maximum height of 650px. It has the aspect
    // ratio of the original image.
    public String getLarge() {
        return large;
    }

    // This image has a maximum width of 1880px and a maximum height of 1300px. It has the aspect
    // ratio of the original image.
    public String getLarge2x() {
        return large2x;
    }


    // 	This image has a height of 350px and a flexible width. It has the aspect ratio of the
    // original image.
    public String getMedium() {
        return medium;
    }

    // This image has a height of 130px and a flexible width. It has the aspect ratio of the
    // original image.
    public String getSmall() {
        return small;
    }


    // This image has a width of 800px and a height of 1200px.
    public String getPortrait() {
        return portrait;
    }


    // This image has a width of 1200px and height of 627px.
    public String getLandscape() {
        return landscape;
    }

    // This image has a width of 280px and height of 200px.
    public String getTiny() {
        return tiny;
    }
}
