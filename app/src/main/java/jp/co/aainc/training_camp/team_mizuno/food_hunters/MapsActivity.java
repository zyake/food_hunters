package jp.co.aainc.training_camp.team_mizuno.food_hunters;

import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.LatLngs;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.managers.MapManager;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.restaurants.SearchRequest;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.tasks.RestaurantSearchAsyncTask;
import jp.co.aainc.training_camp.team_mizuno.food_hunters.utils.DefaultLocationListener;

import static android.location.LocationManager.GPS_PROVIDER;

public class MapsActivity extends ActionBarActivity {

    private static final String MARKER_TITLE = "現在位置";

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private RankingFragment rankingFragment;
    private HistoryFragment historyFragment;

    private LocationManager locManager;

    private MapManager mapManager;

    private boolean isSearching = false;

    private String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mTitles;
    private ActionBarDrawerToggle mDrawerToggle;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Log.i(TAG, "Select Item Position : " + position);

        switch (position)
        {
            case 0:
                getSupportFragmentManager().beginTransaction().show(mapFragment).commit();
                getFragmentManager().beginTransaction().hide(rankingFragment).commit();
                getFragmentManager().beginTransaction().hide(historyFragment).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                getFragmentManager().beginTransaction().show(rankingFragment).commit();
                getFragmentManager().beginTransaction().hide(historyFragment).commit();

                break;
            case 2:
                getSupportFragmentManager().beginTransaction().hide(mapFragment).commit();
                getFragmentManager().beginTransaction().hide(rankingFragment).commit();
                getFragmentManager().beginTransaction().show(historyFragment).commit();
                break;
        }


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_main);
        setUpDrawer();
        setUpMapIfNeeded();
        registerLocationListenerIfNeeded();
        addFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mDrawerToggle.onOptionsItemSelected(item)
                || super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void addFragments() {
        Bundle bundle = new Bundle();
        historyFragment = new HistoryFragment();
        historyFragment.setArguments(bundle);

        // フラグメントをアクティビティに追加する FragmentTransaction を利用する
        FragmentManager manager = getFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, historyFragment, "history_fragment");
        transaction.commit();

        rankingFragment = new RankingFragment();
        rankingFragment.setArguments(bundle);

        transaction = manager.beginTransaction();
        transaction.add(R.id.container, rankingFragment, "ranking_fragment");
        transaction.commit();

    }

    private void setUpDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.slide_menu);
        mTitles = getResources().getStringArray(R.array.drawer_menu_titles);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // ActionBarDrawerToggleクラス内の同メソッドにてアイコンのアニメーションの処理をしている。
                // overrideするときは気を付けること。
                super.onDrawerSlide(drawerView, slideOffset);
//                Log.i(TAG, "onDrawerSlide : " + slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // 表示済み、閉じ済みの状態：0
                // ドラッグ中状態:1
                // ドラッグを放した後のアニメーション中：2
//                Log.i(TAG, "onDrawerStateChanged  new state : " + newState);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            map = mapFragment.getMap();
            if (mapManager == null) {
                mapManager = new MapManager(map);
            }
            mapManager.setMap(map);
        }
    }

    private void startAsync(LatLng latLng) {
        isSearching = true;
        RestaurantSearchAsyncTask asyncTask = new RestaurantSearchAsyncTask() {
            @Override
            protected void onPostExecute(List restaurants) {
                isSearching = false;
                mapManager.replaceMarkers(restaurants);
            }
        };

        asyncTask.execute(new SearchRequest(latLng.latitude, latLng.longitude));
    }

    private void registerLocationListenerIfNeeded() {
        Location lastKnownLocation = locManager.getLastKnownLocation(GPS_PROVIDER);
        if (lastKnownLocation == null) {
            lastKnownLocation = new Location(GPS_PROVIDER);
        }

        mapManager.replaceCurrent(new Location(GPS_PROVIDER), MARKER_TITLE);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (!isSearching) {
                   startAsync(cameraPosition.target);
                }
            }
        });

        startAsync(LatLngs.fromLocation(lastKnownLocation));

        locManager.requestLocationUpdates(GPS_PROVIDER, 1000, 5,
            new DefaultLocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mapManager.replaceCurrent(location, MARKER_TITLE);
                    if (!isSearching) {
                        startAsync(LatLngs.fromLocation(location));
                    }
                }
        });
    }
}