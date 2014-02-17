package com.wi14.team5.place_its;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity that displays more information about one place-it
 */
public class MoreInfoActivity extends Activity   {
	private GoogleMap mMap;
	private TextView title;
	private TextView notes;

	double lat = 0;
	double lng = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_info); 
		setUpMapIfNeeded();
		mMap.setMyLocationEnabled(true);
		Intent intent = getIntent();
        if (intent.getStringExtra(MainActivity.TITLE) == null) return;

        //int status = intent.getIntExtra(MainActivity.STATUS, 0);
        lat = intent.getDoubleExtra(MainActivity.LAT, 0);
        lng = intent.getDoubleExtra(MainActivity.LNG, 0);
        byte recurrence = intent.getByteExtra(MainActivity.RECURRENCE, (byte) 0);
        String placeTitle = intent.getStringExtra(MainActivity.TITLE);
        String snippet = intent.getStringExtra(MainActivity.SNIPPET);

		title = (TextView) findViewById(R.id.textViewPlaceTitle);
		title.setText(placeTitle);
		notes = (TextView) findViewById(R.id.textViewNotesContent);
		notes.setText(snippet);
		((TextView)findViewById(R.id.textViewLocation1)).setText(Double.toString(lat)
				+ ", " + Double.toString(lng));
		if(recurrence != 0) {
			((TextView)findViewById(R.id.textViewInfoRepeat1)).setText("Yes");
		} else {
			((TextView)findViewById(R.id.textViewInfoRepeat1)).setText("No");
		}
		
		centerMapOnMyLocation();
		
	    mMap.addMarker(new MarkerOptions() 
		.position(new LatLng(lat, lng))
		.title(placeTitle)
		.snippet(snippet));		
	}
	
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map. 
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			// Check if we were successful in obtaining the map. 
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
			} 
		}
	}
	
	private void centerMapOnMyLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(
        		locationManager.getBestProvider(criteria, false));
        location.setLatitude(lat);
        location.setLongitude(lng);
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
	}
}
