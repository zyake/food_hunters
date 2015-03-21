package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

import android.location.Location;
import android.location.LocationManager;

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

    private final String urlMobile;

    public Restaurant(double lat, double log, String name, String category, String shopImageUrl,
                      String qrCode, String openTime, String holiday, String prShort, String urlMobile) {
        this.lat = lat;
        this.log = log;
        this.name = name;
        this.category = category;
        this.shopImageUrl = shopImageUrl;
        this.qrCode = qrCode;
        this.openTime = openTime;
        this.holiday = holiday;
        this.prShort = prShort;
        this.urlMobile = urlMobile;
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

    public String getUrlMobile() {
        return urlMobile;
    }

    public Location toLocation() {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(getLat());
        location.setLongitude(getLog());
        return location;
    }
}
