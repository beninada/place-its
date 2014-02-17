package com.wi14.team5.place_its;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MoreInfoActivity extends Activity {
	private GoogleMap mMap;

	private TextView title;
	private TextView location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info);
		
		Intent intent = getIntent();
		setUpMapIfNeeded();

		title = (EditText) findViewById(R.id.textViewPlaceTitle);
		location = (EditText) findViewById(R.id.textViewLocation);
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map. 
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// Check if we were successful in obtaining the map. 
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
                mMap.setMyLocationEnabled(true);
                centerMapOnMyLocation();
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
