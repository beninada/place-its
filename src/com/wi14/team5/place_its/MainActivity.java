package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import com.wi14.team5.place_its.lists.ListPagerAdapter;
import com.wi14.team5.place_its.lists.PlaceItListFragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
	private static ListPagerAdapter lpAdapter;

	/**
	 * The ViewPager that will display the three Place-it lists.
	 */
	private	ViewPager mViewPager;
	
	/**
	 * The SQLiteHandler that deals with database I/O. It must exist for as long as
	 * this activity is alive.
	 */
	private static SQLiteHandler sqlh;

    /**
     * GPSManager manages gps notification functions of place its
     */
    private static GPSManager gpsManager;

    /**
     * Contains every instantiated PlaceIt.
     */
	private static AllPlaceIts allPlaceIts;

	/**
	 * Specifies whether or not onCreate() has been called yet in this life cycle.
	 */
	private static boolean hasBeenCreated = false;
	
	public static final String PLACE_IT = "placeit";
	public static final String STATUS = "status";
	public static final String TITLE = "title";
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String SNIPPET = "snippet";
	public static final String RECURRENCE = "recurrence";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Intent intent = getIntent();

		if (!hasBeenCreated) {
            sqlh = new SQLiteHandler(this);

			if (sqlh.getPlaceItCount() > 0) {
                ArrayList<HashMap<String, PlaceIt>> l = sqlh.getAllPlaceIts();
                allPlaceIts = new AllPlaceIts(l.get(0), l.get(1), l.get(2));
			} else {
				if(allPlaceIts == null) {
					allPlaceIts = new AllPlaceIts();
				}
				lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), null);
			}

            lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), allPlaceIts);
			hasBeenCreated = true;
		} else {
            dealWithIntent(intent);
            lpAdapter = new ListPagerAdapter(getSupportFragmentManager(), allPlaceIts);
		}
		
		setupUI();
		
		// so notifications go to the In Progress tab
	    int tab = intent.getIntExtra("Tab", 0);
	    mViewPager.setCurrentItem(tab);
		
		// register the list for context menu on hold down.
	    ListView lv = (ListView) findViewById(R.id.list);
	    registerForContextMenu(lv);

		if(gpsManager == null) {
			gpsManager = new GPSManager(this, allPlaceIts);
		}
	}
	
	/**
	 * Sets up all user interface items in the main activity.
	 */
	private void setupUI() {
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
	}
	
	/**
	 * Handles an intent sent to the main activity.
	 * @param intent the intent to handle
	 */
	private void dealWithIntent(Intent intent) {
		if (intent != null) {
            if (intent.getStringExtra(TITLE) == null) return;

            int status = intent.getIntExtra(STATUS, 0);
            double lat = intent.getDoubleExtra(LAT, 0);
            double lng = intent.getDoubleExtra(LNG, 0);
            byte recurrence = intent.getByteExtra(RECURRENCE, (byte) 0);
            String title = intent.getStringExtra(TITLE);
            String snippet = intent.getStringExtra(SNIPPET);
            
            // update our allPlaceIts field
            PlaceIt added = new PlaceIt(title, lat, lng, snippet, recurrence, status);
            allPlaceIts.addPlaceIt(added, status);
            
            // update the list fragment's adapter so it reflects changes
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
	        	intent = new Intent(MainActivity.this, AddPlaceItActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
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
	
	@Override
	public void onPause(){
		super.onPause();
		Log.i("On Pause Called", "......................");
		sqlh.addAllPlaceIts(allPlaceIts);
		Log.i("After write, database has", new Integer(sqlh.getPlaceItCount()).toString());
	}

}
