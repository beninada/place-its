package com.wi14.team5.place_its;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddPlaceitActivity extends Activity implements OnMapClickListener, OnCheckedChangeListener {
	
	private GoogleMap mMap;
	private EditText title;
	private EditText notes;
	private boolean placeitAdded;
	private boolean isRecurring;
	private Marker added;
	Intent recurring;
	Dialog d;
	RadioGroup firstRadioGroup, secondRadioGroup;
	RadioButton radioWeekly, radioDaily, radioTest;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_placeit);
		setUpMapIfNeeded();
		mMap.setMyLocationEnabled(true);
		mMap.setOnMapClickListener(this);
		//mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
		title = (EditText) findViewById(R.id.editPlaceItTitle);
		notes = (EditText) findViewById(R.id.editTextNotes);
		centerMapOnMyLocation();
		placeitAdded = false; //didn't add yet
		recurring = new Intent(AddPlaceitActivity.this, RecurringPlaceitActivity.class);
		isRecurring = false;	


	}

	@Override
	public void onMapClick(LatLng point) {
			if (added != null) added.remove();
			
			added = mMap.addMarker(new MarkerOptions() 
					.position(point)
					.title(title.getText().toString())
					.snippet(notes.getText().toString()));	
			
			//LatLng pos = added.getPosition();
			if (title.getText().toString().length() != 0) {
				added.showInfoWindow();
			}
			
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

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
	}
	
	public void onSubmit(View view) {
		Toast.makeText(AddPlaceitActivity.this, "Tag added!", Toast.LENGTH_SHORT).show();
		added.setTitle(title.getText().toString());
		added.setSnippet(notes.getText().toString());
	}
	
	public void onRecurringClick(View view) {

		if (!isRecurring) {
			d = new Dialog(this);
	        Window w = d.getWindow(); 
	        w.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, 
	                   WindowManager.LayoutParams.FLAG_BLUR_BEHIND); 
	        d.setTitle("Recurring Schedule"); 
	        d.setContentView(R.layout.activity_recurring_placeit); 
	        
	        d.show(); 
	        
	        Button recurringCancel = (Button)d.findViewById(R.id.buttonRecurringCancel);

	        if(recurringCancel != null) {
		        recurringCancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						d.cancel();
						ToggleButton repeat = (ToggleButton)findViewById(R.id.toggleButton1);
						repeat.setChecked(false);
						isRecurring = false;
						return;
					}
				});
	        }
	        firstRadioGroup = (RadioGroup) d.findViewById(R.id.firstRadioGroup);
	        radioWeekly = (RadioButton) d.findViewById(R.id.radioButtonWeekly);
	        secondRadioGroup = (RadioGroup) d.findViewById(R.id.secondRadioGroup);
	        radioDaily = (RadioButton) d.findViewById(R.id.radioButtonDaily);
	        radioTest = (RadioButton) d.findViewById(R.id.radioButtonTest);


	        radioWeekly.setOnCheckedChangeListener(this); // implement OnCheckedChangeListener to the current class
	        radioDaily.setOnCheckedChangeListener(this);
	        radioTest.setOnCheckedChangeListener(this);
	        
	        Button recurringSubmit = (Button)d.findViewById(R.id.buttonRecurringOK);
	        if(recurringSubmit != null) {
		        recurringSubmit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						d.hide();
						ToggleButton repeat = (ToggleButton)findViewById(R.id.toggleButton1);
						
				        radioWeekly = (RadioButton) d.findViewById(R.id.radioButtonWeekly);
				        radioDaily = (RadioButton) d.findViewById(R.id.radioButtonDaily);
				        radioTest = (RadioButton) d.findViewById(R.id.radioButtonTest);
				        
				        if (radioWeekly.isChecked()) {			    
				        	repeat.setText("WEEKLY");
				        }
				        else if(radioDaily.isChecked()) {
				        	repeat.setText("DAILY");
				        }
				        else if (radioTest.isChecked()) {
				        	repeat.setText("TEST MODE");
				        }
				        else {
				        	repeat.setText("OFF");
							repeat.setChecked(false);
							isRecurring = false;
							return;
				        }

						
						
						
						
					}
				});
	        }
	        

	        
	        isRecurring = true;
		}
		else {
			isRecurring = false;
		}
        
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
	   
		firstRadioGroup.clearCheck();
	    secondRadioGroup.clearCheck();		
	}
	
}

