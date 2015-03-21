package jp.co.aainc.training_camp.team_mizuno.food_hunters.managers;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.Restaurant;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.LatLngs;

public class MapManager {

    private final Context context
            ;
    private GoogleMap map;

    private Marker currentMarker;

    private final List<Marker> currentLocations = new ArrayList<>();

    private final Map<Marker, Restaurant> markerRestaurantMap = new HashMap<>();

    public MapManager(GoogleMap map, Context applicationContext) {
        setMap(map);
        this.context = applicationContext;
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
                IconGenerator generator = new IconGenerator(context);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(generator.makeIcon(restaurant.getName())));

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
            Marker marker = map.addMarker(new MarkerOptions().position(latLng));
            markerRestaurantMap.put(marker, entity);
            currentLocations.add(marker);
        }
    }
}
