package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

import com.google.android.gms.maps.model.LatLng;

public class Restaurant {

    private final double lat;

    private final double log;

    private final String name;

    private final String category;

    private final String shopImageUrl;

    private final String qrCode;

    private final String openTime;

    private final String holiday;

    private final String prShort;

    public Restaurant(double lat, double log, String name, String category, String shopImageUrl,
                      String qrCode, String openTime, String holiday, String prShort) {
        this.lat = lat;
        this.log = log;
        this.name = name;
        this.category = category;
        this.shopImageUrl = shopImageUrl;
        this.qrCode = qrCode;
        this.openTime = openTime;
        this.holiday = holiday;
        this.prShort = prShort;
    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getShopImageUrl() {
        return shopImageUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getPrShort() {
        return prShort;
    }

    public LatLng toLatLng() {
        return new LatLng(getLat(), getLog());
    }
}
