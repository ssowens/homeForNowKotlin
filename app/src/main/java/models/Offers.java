package models;

import com.google.gson.annotations.SerializedName;

public class Offers {

    private String id;
    private String rateCode;
    private RateFamilyEstimate rateFamilyEstimate;
    private Room room;

    private Guests guests;

    private class Guests {
        private String adults;
    }

    private Price price;

    private class Price {
        private String currency;
        private String total;
    }

    @SerializedName("self")
    public String url;
}
