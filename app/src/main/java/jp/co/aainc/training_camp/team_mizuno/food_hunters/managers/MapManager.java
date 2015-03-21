package jp.co.aainc.training_camp.team_mizuno.food_hunters.managers;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.LatLngs;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;

public class MapManager {

    private GoogleMap map;

    private Marker currentMarker;

    private final List<Marker> currentLocations = new ArrayList<>();

    private final Map<Marker, Restaurant> markerRestaurantMap = new HashMap<>();

    public MapManager(GoogleMap map) {
        setMap(map);
    }

    public void setMap(final GoogleMap map) {
        if (map != null) {
            map.setOnMarkerClickListener(null);
        }
        this.map = map;
        currentLocations.clear();
        currentMarker = null;
        markerRestaurantMap.clear();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Restaurant restaurant = markerRestaurantMap.get(marker);
                map.addGroundOverlay(new GroundOverlayOptions().position(marker.getPosition(), 3, 3).image(BitmapDescriptorFactory.defaultMarker()));
                return false;
            }
        });
    }

    public void replaceCurrent(Location loc, String title) {
        if (currentMarker != null) {
            currentMarker.remove();
            currentMarker = null;
        }

        LatLng latLng = LatLngs.fromLocation(loc);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        currentMarker = map.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    public void replaceMarkers(List<Restaurant> restaurants) {
        for (Marker loc : currentLocations) {
            loc.remove();
        }
        currentLocations.clear();

        for (Restaurant entity : restaurants) {
            LatLng latLng = entity.toLatLng();
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(entity.getName()));
            markerRestaurantMap.put(marker, entity);
            currentLocations.add(marker);
        }
    }
}
