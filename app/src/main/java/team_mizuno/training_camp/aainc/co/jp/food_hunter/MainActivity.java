package team_mizuno.training_camp.aainc.co.jp.food_hunter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.content.res.Configuration;
import android.view.Window;
import android.view.LayoutInflater;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

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
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
                Log.i(TAG, "onDrawerSlide : " + slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // 表示済み、閉じ済みの状態：0
                // ドラッグ中状態:1
                // ドラッグを放した後のアニメーション中：2
                Log.i(TAG, "onDrawerStateChanged  new state : " + newState);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

}

