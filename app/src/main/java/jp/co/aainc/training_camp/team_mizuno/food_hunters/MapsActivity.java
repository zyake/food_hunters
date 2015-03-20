package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.managers.MapManager;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.RestaurantSearcher;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.RestaurantSearcherFactory;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.SearchRequest;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.tasks.RestaurantSearchAsyncTask;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.DefaultLocationListener;

import static android.location.LocationManager.GPS_PROVIDER;

public class MapsActivity extends FragmentActivity {

    private static final String MARKER_TITLE = "現在位置";

    private GoogleMap map;

    private LocationManager locManager;

    private MapManager mapManager;

    private RestaurantSearcher searcher = RestaurantSearcherFactory.newSearcher();

    private boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        registerLocationListenerIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (mapManager == null) {
                mapManager = new MapManager(map);
            }
            mapManager.setMap(map);
        }
    }

    private void startAsync(Location loc) {
        isSearching = true;
        RestaurantSearchAsyncTask asyncTask = new RestaurantSearchAsyncTask() {
            @Override
            protected void onPostExecute(List restaurants) {
                isSearching = false;
                mapManager.replaceMarkers(restaurants);
            }
        };

        asyncTask.execute(new SearchRequest(loc.getLatitude(), loc.getLongitude()));
    }

    private void registerLocationListenerIfNeeded() {
        Location lastKnownLocation = locManager.getLastKnownLocation(GPS_PROVIDER);
        mapManager.setCurrent(lastKnownLocation, MARKER_TITLE);
        startAsync(lastKnownLocation);
        locManager.requestLocationUpdates(GPS_PROVIDER, 1000, 5,
            new DefaultLocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mapManager.setCurrent(location, MARKER_TITLE);
                    if (!isSearching) {
                        startAsync(location);
                    }
                }
        });
    }
}
