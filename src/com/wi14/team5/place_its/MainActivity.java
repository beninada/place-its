package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.wi14.team5.place_its.lists.ListPagerAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
	SQLiteHandler db;
	
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
	 * The GoogleMap that is used throughout this app.
	 */
    private GoogleMap mMap;
    
    /**
     * GPSManager manages gps notification functions of place its
     */
    private GPSManager gpsManager;

    /**
     * Contains every instantiated PlaceIt.
     */
	private AllPlaceIts allPlaceIts;

	/**
	 * Sets up the working environment for the app.
	 * This method brings the app back to its state before onDestroy() was called.
	 * Should be called if there are place-its in the database.
	 */
	private void setup() {
		// Do a null check to confirm that we have not already instantiated the map. 
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map. 
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				ArrayList<HashMap<String, PlaceIt>> l = sqlh.getAllPlaceIts(mMap);
				allPlaceIts = new AllPlaceIts(l.get(0), l.get(1), l.get(2));
			} 
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new SQLiteHandler(this);

		// get a handler to the database
		sqlh = new SQLiteHandler(this);

		if (sqlh.getPlaceItCount() > 0) {
			setup();
			lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), allPlaceIts);
		} else {
			lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), null);
		}

		if(gpsManager == null) {
			gpsManager = new GPSManager(this, allPlaceIts);
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

		// for each list, add a tab to the action bar with text corresponding to page title.
		for (int i = 0; i < lpAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					                  .setText(lpAdapter.getPageTitle(i))
					                  .setTabListener(this));
		}
		
		// register the list for context menu on hold down.
	    ListView lv = (ListView) findViewById(R.id.list);
	    registerForContextMenu(lv);
	    
	    //So notifications go to the In Progress tab
	    Intent intent = getIntent();
	    int tab = intent.getIntExtra("Tab", 0);
	    mViewPager.setCurrentItem(tab);
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

	public AllPlaceIts getAllPlaceIts() {
		return allPlaceIts;
	}

}
