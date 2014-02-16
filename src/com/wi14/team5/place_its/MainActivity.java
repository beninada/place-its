package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import com.wi14.team5.place_its.lists.ListPagerAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/**
 * Fragment Activity that contains the three lists: To Do, In Progress, and
 * Completed. They are displayed in tabs that the user can select/scroll
 * through.
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {	
	/**
	 * A PagerAdapter that provides the Place-it list fragments.
	 */
	private ListPagerAdapter lpAdapter;

	/**
	 * The ViewPager that will display the three Place-it lists.
	 */
	private	ViewPager mViewPager;
	
	/**
	 * The SQLiteHandler that deals with database I/O. It must exist for as long as
	 * this activity is alive.
	 */
	private SQLiteHandler sqlh;

    /**
     * GPSManager manages gps notification functions of place its
     */
    private GPSManager gpsManager;

    /**
     * Contains every instantiated PlaceIt.
     */
	private AllPlaceIts allPlaceIts;
	
	public static final String PLACE_IT = "placeit";
	public static final String STATUS = "status";
	public static final String TITLE = "title";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String SNIPPET = "snippet";
	public static final String RECURRENCE = "recurrence";

	private boolean hasBeenCreated = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// if the app has not been created yet...
		if (!hasBeenCreated) {
            sqlh = new SQLiteHandler(this);

            // if there are place-its in the db, add them to the lists
			if (sqlh.getPlaceItCount() > 0) {
                ArrayList<HashMap<String, PlaceIt>> l = sqlh.getAllPlaceIts();
                allPlaceIts = new AllPlaceIts(l.get(0), l.get(1), l.get(2));
                lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), allPlaceIts);
			} else {
				allPlaceIts = new AllPlaceIts();
				lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), null);
			}

			hasBeenCreated = true;
		}

		Intent intent = getIntent();
		if (intent != null) {
			dealWithIntent(intent);
			lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), allPlaceIts);
		}

		// set up the action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// attach the adapter to the ViewPager
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(lpAdapter);
		
		// listen for user swipes between lists
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

	    //So notifications go to the In Progress tab
	    int tab = intent.getIntExtra("Tab", 0);
	    mViewPager.setCurrentItem(tab);

		// for each list, add a tab to the action bar with text corresponding to page title.
		for (int i = 0; i < lpAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					                  .setText(lpAdapter.getPageTitle(i))
					                  .setTabListener(this));
		}
		
		// register the list for context menu on hold down.
	    ListView lv = (ListView) findViewById(R.id.list);
	    registerForContextMenu(lv);

		if(gpsManager == null) {
			gpsManager = new GPSManager(this, allPlaceIts);
		}
	}
	
	private void dealWithIntent(Intent intent) {
		Bundle extras = null;
		if (intent != null) {
			extras = intent.getExtras();
		}

		// if we received an intent, add the new place-it to AllPlaceIts
		if (extras != null) {
            int status = extras.getInt(STATUS, 0);
            double lat = extras.getDouble(LAT, 0);
            double lng = extras.getDouble(LNG, 0);
            byte recurrence = extras.getByte(RECURRENCE, (byte) 0);
            String title = extras.getString(TITLE);
            String snippet = extras.getString(SNIPPET);

            PlaceIt added = new PlaceIt(title, lat, lng, snippet, recurrence, status);
            allPlaceIts.addPlaceIt(added, status);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

	    // handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_map:
	        	intent = new Intent(MainActivity.this, MapActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.action_new:
	        	intent = new Intent(MainActivity.this, AddPlaceitActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            menu.add("TO DO");
            menu.add("IN PROGRESS");
            menu.add("COMPLETED");
        }
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) { }

}
