package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Displays all place-its on a large map. When a place-it is clicked, a modal
 * summarizes the place-it.
 */
public class MapActivity extends Activity {
	private GoogleMap mMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.map, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

	    // handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_list:
	        	intent = new Intent(MapActivity.this, MainActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.action_new:
	        	intent = new Intent(MapActivity.this, AddPlaceItActivity.class);
	        	startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map. 
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map. 
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
                centerMapOnMyLocation();
                mMap.setMyLocationEnabled(true);

                SQLiteHandler sqlh = new SQLiteHandler(this);

                // if there are place-its in the db, add them to the map
                if (sqlh.getPlaceItCount() > 0) {
                    ArrayList<HashMap<String, PlaceIt>> l = sqlh.getAllPlaceIts();
                    
                    // iterate over each hash map in the array list
                    for (HashMap<String, PlaceIt> h : l) {
                    	// get all place-its in each slot of the hash map
                    	for (PlaceIt p : h.values()) {
                            mMap.addMarker(new MarkerOptions() 
                                    	.position(new LatLng(p.getLat(), p.getLng()))
                                    	.title(p.getName())
                                    	.snippet(p.getDescription()));
                    	}
                    }
                }
			} 
		}
	}
	
	private void centerMapOnMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(
        		locationManager.getBestProvider(criteria, false));

        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
	}
	
	private void zoomToFitAllMarkers(ArrayList<Marker> markers) {
        Builder builder = new Builder();
        for (Marker m : markers) {
        	builder.include(m.getPosition());
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 
                this.getResources().getDisplayMetrics().widthPixels, 
                this.getResources().getDisplayMetrics().heightPixels, 
                50));
	}
}
