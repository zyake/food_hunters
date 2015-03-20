package jp.co.aainc.training_camp.team_mizuno.food_hunters.managers;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.geo.GeoEntity;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.geo.LatLngs;

public class MapManager {

    private GoogleMap map;

    private Marker currentMarker;

    private final List<Marker> currentLocations = new ArrayList<>();

    public MapManager(GoogleMap map) {
        this.map = map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setCurrent(Location loc, String title) {
        if (currentMarker != null) {
            currentMarker.remove();
            currentMarker = null;
        }

        LatLng latLng = LatLngs.fromLocation(loc);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        currentMarker = map.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    public void replaceMarkers(List<GeoEntity> entities) {
        for (Marker loc : currentLocations) {
            loc.remove();
            currentLocations.clear();
        }

        for (GeoEntity entity : entities) {
            LatLng latLng = LatLngs.fromEntity(entity);
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(entity.getName()));
            entity.configMarker(marker);
        }
    }
}
