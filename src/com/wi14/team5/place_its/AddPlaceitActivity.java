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
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class AddPlaceitActivity extends Activity implements OnMapClickListener, OnCheckedChangeListener, OnFocusChangeListener {
	
	private GoogleMap mMap;
	private EditText title;
	private EditText notes;
	private EditText address;
	private boolean placeitAdded;
	private boolean isRecurring;
	private Marker added;
	private Intent recurring;
	private Button cancelButton;
	Dialog d;
	RadioGroup firstRadioGroup, secondRadioGroup;
	RadioButton radioWeekly, radioDaily, radioTest;
	byte recurrence;
	
	
	
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
		address = (EditText) findViewById(R.id.editPlaceitAddress);
		address.setOnFocusChangeListener(this);
		centerMapOnMyLocation();
		placeitAdded = false; //didn't add yet
		recurring = new Intent(AddPlaceitActivity.this, RecurringPlaceitActivity.class);
		cancelButton = (Button) findViewById(R.id.buttonCancel);
	    cancelButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            Intent intent = new Intent(AddPlaceitActivity.this, MainActivity.class);
	            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   
	            startActivity(intent);
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
			
			//LatLng pos = added.getPosition();
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
				//something didn't work
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
		if (added == null) {
			Toast.makeText(AddPlaceitActivity.this, "Please select a location", Toast.LENGTH_LONG).show();

		}
		else if (title.getText().length() != 0) {
			//Toast.makeText(AddPlaceitActivity.this, "Placed it!", Toast.LENGTH_SHORT).show();
			added.setTitle(title.getText().toString());
			
			if (notes.getText().length() != 0) {
				added.setSnippet(notes.getText().toString());
			}
			else {
				added.setSnippet("");
			}
			
			Intent intent = new Intent(AddPlaceitActivity.this, MainActivity.class);
			Bundle extras = new Bundle();

			// bundle all place-it info up
			extras.putInt(MainActivity.STATUS, 0);
			extras.putDouble(MainActivity.LAT, added.getPosition().latitude);
			extras.putDouble(MainActivity.LNG, added.getPosition().longitude);
			extras.putByte(MainActivity.RECURRENCE, recurrence);
			extras.putString(MainActivity.TITLE, title.getText().toString());
			extras.putString(MainActivity.SNIPPET, added.getSnippet());

			// and send it all over with the intent
			intent.putExtra(MainActivity.PLACE_IT, extras);

			startActivity(intent);
		}
		else {
			Toast.makeText(AddPlaceitActivity.this, "Place-it must have a title", Toast.LENGTH_LONG).show();
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
				        	//010101010
				        	for(int i = 0; i < firstRadioGroup.getChildCount(); i++){
				        		if(firstRadioGroup.getChildAt(i).isSelected() == true)
				        			recurrence = (byte) (recurrence | (1 << i));
				        	}
				        }
				        else if(radioDaily.isChecked()) {
				        	repeat.setText("DAILY");
				        	//011111111
				        	for(int i = 0; i < 8; i++){
				        		recurrence = (byte) (recurrence | (1 << i));
				        	}
				        }
				        else if (radioTest.isChecked()) {
				        	repeat.setText("TEST MODE");
				        	//10000000
				        	recurrence = (byte) (recurrence | (1 << 7));
				        	for(int i = 0; i < 7; i++){
				        		recurrence = (byte) (recurrence & ~(1 << i));
				        	}
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

	@Override
	public void onFocusChange(View view, boolean arg1) {
		//LatLng pos = added.getPosition();
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
				
				//LatLng pos = added.getPosition();
				if (title.getText().toString().length() != 0) {
					added.showInfoWindow();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}

