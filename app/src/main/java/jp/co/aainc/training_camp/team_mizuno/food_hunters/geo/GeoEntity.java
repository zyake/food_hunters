package jp.co.aainc.training_camp.team_mizuno.food_hunters.geo;

import com.google.android.gms.maps.model.Marker;

public interface GeoEntity {

    double getLat();

    double getLog();

    String getName();

    void configMarker(Marker marker);
}