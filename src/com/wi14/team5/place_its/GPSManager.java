package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;

public class GPSManager implements LocationListener{
	
public static final float RADIUS = 804.67f; // half mile radius to place-it
public static final int gpsNotifyID = 1337;
	
	NotificationManager mNotificationManager;
	LocationManager locationManager;
	Location currentTarget;
	Activity activity;
	AllPlaceIts placeIts;
	int ID;	
	Collection<PlaceIt> listToCompare;

	
	public GPSManager(Activity activity, AllPlaceIts allPlaceIts) {
		this.activity = activity;
		placeIts = allPlaceIts;
		ID = 0;
		listToCompare = new ArrayList<PlaceIt>(10);
		
		mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
		locationManager =  (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	 0, this);
		
		currentTarget = new Location("Current Target");
		currentTarget.setLatitude(0);
		currentTarget.setLongitude(0);

		//this block is needed here for when app was closed, and this check is needed
		// if gps is on when gps was off when app was closed
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			mNotificationManager.cancel(gpsNotifyID);
		} else {
			createGPSNotification();
		}
		
	}
	
	public void createGPSNotification() {
		 NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(activity)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle("GPS is not Enabled")
			        .setContentText("Place-its will not function correctly")
			        .setTicker("Place-its needs GPS enabled");
		 
		 
			mBuilder.setOngoing(true);
			mNotificationManager.notify(gpsNotifyID, mBuilder.build());	    
	 }
	
	public void createNotification(PlaceIt placeit) {
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(activity)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Nearby Place-it!")
		        .setContentText(placeit.getName())
		        .setTicker(placeit.getDescription());
	 
		
		Intent intent = new Intent(activity, activity.getClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("Tab", 1);//so the In Progress tab is selected when notification is tapped on
	 	PendingIntent resultIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultIntent);
		// ID allows you to update the notification later on.
		mBuilder.setAutoCancel(true); //clears notification if tapped on
		mBuilder.setLights(0xFF0000FF, 1500, 1500); //blue light 1.5sec on/off cycle
		mBuilder.setVibrate(new long[]{0, 1000}); //vibrates once for 1sec
		mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI); //plays the default notification sound
		mNotificationManager.notify(ID++, mBuilder.build());
		    
	 }

	@Override
	public void onLocationChanged(Location location) {
		if(placeIts != null && !placeIts.getTODO().isEmpty()) {
			listToCompare.clear();
			listToCompare.addAll(placeIts.getTODO().values());
			for(PlaceIt placeit : listToCompare){
				LatLng position = new LatLng(placeit.getLat(), placeit.getLng());
				currentTarget.setLatitude(position.latitude);
				currentTarget.setLongitude(position.longitude);
				if(location.distanceTo(currentTarget) <= RADIUS){
					createNotification(placeit);
					placeIts.updatePlaceIt(placeit, 1);
				}
			}
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			createGPSNotification();
		}
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			mNotificationManager.cancel(gpsNotifyID);
		}
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {	
	}
	
	public int getNotificationID() {
		return this.ID;
	}

}
