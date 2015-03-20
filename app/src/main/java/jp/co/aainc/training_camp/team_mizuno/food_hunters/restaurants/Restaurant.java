package jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants;

import com.google.android.gms.maps.model.Marker;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.geo.GeoEntity;

public class Restaurant implements GeoEntity {

    private final double lat;

    private final double log;

    private final String name;

    public Restaurant(double lat, double log, String name) {
        this.lat = lat;
        this.log = log;
        this.name = name;
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLog() {
        return log;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void configMarker(Marker marker) {
    }
}
