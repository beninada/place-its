package com.wi14.team5.place_its;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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

public class AddPlaceItActivity extends Activity
	implements OnMapClickListener, OnCheckedChangeListener, OnFocusChangeListener {

	private GoogleMap mMap;
	private Marker added;

	private EditText title;
	private EditText notes;
	private EditText address;
	private Button cancelButton;
	private RadioButton radioWeekly, radioDaily, radioTest;
	private RadioGroup firstRadioGroup, secondRadioGroup;
	private Dialog d;

	private boolean isRecurring;
	byte recurrence;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_placeit);
		setUpMapIfNeeded();
		mMap.setMyLocationEnabled(true);
		mMap.setOnMapClickListener(this);

		title = (EditText) findViewById(R.id.editPlaceItTitle);
		notes = (EditText) findViewById(R.id.editTextNotes);
		address = (EditText) findViewById(R.id.editPlaceitAddress);
		address.setOnFocusChangeListener(this);

		centerMapOnMyLocation();

		cancelButton = (Button) findViewById(R.id.buttonCancel);
	    cancelButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	finish();
	        }
	    });

		isRecurring = false;	
	}

	@Override
	public void onMapClick(LatLng point) {
			if (added != null) added.remove();
			
			added = mMap.addMarker(new MarkerOptions() 
					.position(point)
					.title(title.getText().toString())
					.snippet(notes.getText().toString()));	
			
			if (title.getText().toString().length() != 0) {
				added.showInfoWindow();
			}
			
			Geocoder geo = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = geo.getFromLocation(added.getPosition().latitude, 
															  added.getPosition().longitude,
															  5);
				if (addresses.size() > 0) {
					if (addresses.get(0).getLocality() != null) {
						if (addresses.get(0).getSubThoroughfare() != null)
							address.setText(addresses.get(0).getSubThoroughfare() + " " + 
											addresses.get(0).getThoroughfare() + ", " + 
											addresses.get(0).getLocality());
						else {
							address.setText(addresses.get(0).getThoroughfare() + ", " + 
											addresses.get(0).getLocality());							
						}
					}
					else {
						if (addresses.get(0).getSubThoroughfare() != null)
							address.setText(addresses.get(0).getSubThoroughfare() + " " + 
											addresses.get(0).getThoroughfare() + ", " + 
											addresses.get(0).getCountryName());
						else {
							address.setText(addresses.get(0).getThoroughfare() + ", " + 
											addresses.get(0).getCountryName());							
						}
					}
				}
			}
			catch (IOException e) {
			}
	}
	
	public void onSubmit(View view) {
		if (added == null) {
			Toast.makeText(AddPlaceItActivity.this, "Please select a location", Toast.LENGTH_LONG).show();

		}
		else if (title.getText().length() != 0) {
			added.setTitle(title.getText().toString());
			
			if (notes.getText().length() != 0) {
				added.setSnippet(notes.getText().toString());
			}
			else {
				added.setSnippet("");
			}
			
			// bundle up info about the new place-it to send to the main activity
			Intent intent = new Intent(AddPlaceItActivity.this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			intent.putExtra(MainActivity.STATUS, 0);
			intent.putExtra(MainActivity.LAT, added.getPosition().latitude);
			intent.putExtra(MainActivity.LNG, added.getPosition().longitude);
			intent.putExtra(MainActivity.RECURRENCE, recurrence);
			intent.putExtra(MainActivity.TITLE, title.getText().toString());
			intent.putExtra(MainActivity.SNIPPET, added.getSnippet());

			startActivity(intent);
		}
		else {
			Toast.makeText(AddPlaceItActivity.this, "Place-it must have a title",
					Toast.LENGTH_LONG).show();
		}
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

	        // implement OnCheckedChangeListener to the current class
	        radioWeekly.setOnCheckedChangeListener(this); 
	        radioDaily.setOnCheckedChangeListener(this);
	        radioTest.setOnCheckedChangeListener(this);
	        
	        Button recurringSubmit = (Button)d.findViewById(R.id.buttonRecurringOK);
	        if(recurringSubmit != null) {
		        recurringSubmit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						d.hide();
						ToggleButton repeat = (ToggleButton)findViewById(R.id.toggleButton1);
						recurrence = (byte)0;
						
						ToggleButton sunToggle = (ToggleButton) d.findViewById(R.id.buttonSun);
						ToggleButton monToggle = (ToggleButton) d.findViewById(R.id.buttonMon);
						ToggleButton tueToggle = (ToggleButton) d.findViewById(R.id.buttonTue);
						ToggleButton wedToggle = (ToggleButton) d.findViewById(R.id.buttonWed);
						ToggleButton thuToggle = (ToggleButton) d.findViewById(R.id.buttonThurs);
						ToggleButton friToggle = (ToggleButton) d.findViewById(R.id.buttonFri);
						ToggleButton satToggle = (ToggleButton) d.findViewById(R.id.buttonSat);
						
				        radioWeekly = (RadioButton) d.findViewById(R.id.radioButtonWeekly);
				        radioDaily = (RadioButton) d.findViewById(R.id.radioButtonDaily);
				        radioTest = (RadioButton) d.findViewById(R.id.radioButtonTest);
				        
				        if (radioWeekly.isChecked()) {			    
				        	repeat.setText("WEEKLY");
				        	//010101010
				        	if(sunToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x40);
				        	} 
				        	if(monToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x20);
				        	} 
				        	if(tueToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x10);
				        	}
				        	if(wedToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x08);
				        	} 
				        	if(thuToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x04);
				        	} 
				        	if(friToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x02);
				        	} 
				        	if(satToggle.isChecked()) {
				        		recurrence = (byte) (recurrence | (byte)0x01);
				        	}
				        }
				        else if(radioDaily.isChecked()) {
				        	repeat.setText("DAILY");
				        	//011111111
				        	recurrence = (byte)0x7F;
				        }
				        else if (radioTest.isChecked()) {
				        	repeat.setText("10 Seconds");
				        	//10000000
				        	recurrence = (byte)0x80;
				        }
				        else {
				        	repeat.setText("OFF");
							repeat.setChecked(false);
							isRecurring = false;
							recurrence = (byte)0;
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

	@Override
	public void onFocusChange(View view, boolean arg1) {
		Geocoder geo = new Geocoder(this, Locale.getDefault());

		String userAddress = address.getText().toString();
		try {
			List<Address> addresses = geo.getFromLocationName(userAddress, 5);
			if (addresses.size() > 0) {
				LatLng move = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude()), 16));
				
				if (added != null) added.remove();
				
				added = mMap.addMarker(new MarkerOptions() 
						.position(move)
						.title(title.getText().toString())
						.snippet(notes.getText().toString()));	
				
				if (title.getText().toString().length() != 0) {
					added.showInfoWindow();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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

        Location location = locationManager.getLastKnownLocation(
        		locationManager.getBestProvider(criteria, false));
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
	}
}
