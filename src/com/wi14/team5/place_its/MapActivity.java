package com.wi14.team5.place_its;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

	    // handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_list:
	        	intent = new Intent(MapActivity.this, MainActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.action_new:
	        	intent = new Intent(MapActivity.this, AddPlaceitActivity.class);
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
			} 
		}
	}
	
	private void centerMapOnMyLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
	}
}
