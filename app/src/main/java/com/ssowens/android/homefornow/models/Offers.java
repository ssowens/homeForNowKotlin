package com.ssowens.android.homefornow.models;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class Offers extends BaseObservable {

    private String id;
    private String rateCode;
    private RateFamilyEstimated rateFamilyEstimated;
    private String code;
    private String type;
    private Room room;
    private Room.Description description;
    private Guests guests;
    private Price price;

    @SerializedName("self")
    public String self;

    public String getId() {
        return id;
    }

    public String getRateCode() {
        return rateCode;
    }

    public RateFamilyEstimated getRateFamilyEstimated() {
        return rateFamilyEstimated;
    }

    public Room getRoom() {
        return room;
    }

    public Room.Description getDescription() {
        return description;
    }

    public Guests getGuests() {
        return guests;
    }

    public Price getPrice() {
        return price;
    }

    public String getSelf() {
        return self;
    }

    public static class Room extends BaseObservable {

        private String type;
        private TypeEstimated typeEstimated;
        private Description description;

        public String getType() {
            if (!TextUtils.isEmpty(type)) {
                return type;
            } else return "";
        }

        public TypeEstimated getTypeEstimated() {
            return typeEstimated;
        }

        public Description getDescription() {
            return description;
        }

        static class TypeEstimated {
            private int beds;
            private String bedType;

            public int getBeds() {
                return beds;
            }

            public String getBedType() {
                if (!TextUtils.isEmpty(bedType)) {
                    return bedType;
                } else {
                    return "";
                }
            }

            @Override
            public String toString() {
                return "TypeEstimated{" +
                        "beds=" + beds +
                        ", bedType='" + bedType + '\'' +
                        '}';
            }

        }

        static class Description extends BaseObservable {
            private String lang;
            private String text;

            public String getLang() {
                return lang;
            }

            public String getText() {
                return text;
            }

            @Override
            public String toString() {
                return "Description{" +
                        "lang='" + lang + '\'' +
                        ", text='" + text + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Room{" +
                    "type='" + type + '\'' +
                    ", typeEstimated=" + typeEstimated +
                    ", description=" + description +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Offers{" +
                "id='" + id + '\'' +
                ", rateCode='" + rateCode + '\'' +
                ", rateFamilyEstimated=" + rateFamilyEstimated +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", room=" + room +
                ", description=" + description +
                ", guests=" + guests +
                ", price=" + price +
                ", self='" + self + '\'' +
                '}';
    }

    public static class Guests extends BaseObservable {
        private String adults;

        public String getAdults() {
            return adults;
        }

        @Override
        public String toString() {
            return "Guests{" +
                    "adults='" + adults + '\'' +
                    '}';
        }
    }

    public static class RateFamilyEstimated extends BaseObservable {
        private String code;
        private String type;

        public String getCode() {
            return code;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "RateFamilyEstimated{" +
                    "code='" + code + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class Price extends BaseObservable {

        private String currency;
        private String total;

        public String getCurrency() {
            return currency;
        }

        public String getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "Price{" +
                    "currency='" + currency + '\'' +
                    ", total='" + total + '\'' +
                    '}';
        }
    }
}
