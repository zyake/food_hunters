package jp.co.aainc.training_camp.team_mizuno.food_hunters.geo;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class LatLngs {

    public static LatLng fromLocation(Location loc) {
        return new LatLng(loc.getLatitude(), loc.getLongitude());
    }

    public static LatLng fromEntity(GeoEntity entity) {
        return new LatLng(entity.getLat(), entity.getLog());
    }
}
