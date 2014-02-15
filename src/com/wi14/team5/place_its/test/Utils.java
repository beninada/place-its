package com.wi14.team5.place_its.test;

import java.util.ArrayList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wi14.team5.place_its.AllPlaceIts;
import com.wi14.team5.place_its.PlaceIt;

public class Utils {
	/**
	 * Fills up the parameterized GoogleMap with numMarkersToFill Markers.
	 * @param map
	 * @param numMarkersToFill
	 * @return the filled AllPlaceIts data structure.
	 */
	public static AllPlaceIts fillAllPlaceIts(GoogleMap map, int numMarkersToFill) {
		AllPlaceIts allPlaceIts = new AllPlaceIts();

		ArrayList<Marker> todoArr = new ArrayList<Marker>(25);
		ArrayList<Marker> ipArr = new ArrayList<Marker>(25);
		ArrayList<Marker> compArr = new ArrayList<Marker>(25);
		
		for (int i = 0; i < numMarkersToFill; i++) {
			todoArr.add(map.addMarker(new MarkerOptions()
						   .position(new LatLng(37 + (2*i), 122 + (2*i)))
						   .title("A random location")
						   .snippet("A snippet")));
			ipArr.add(map.addMarker(new MarkerOptions()
						 .position(new LatLng(37 + (2*i), 122 + (2*i)))
						 .title("A random location")
						 .snippet("A snippet")));
			compArr.add(map.addMarker(new MarkerOptions()
						   .position(new LatLng(37 + (2*i), 122 + (2*i)))
						   .title("A random location")
						   .snippet("A snippet")));
		}
		
		for (int i = 0; i < numMarkersToFill; i++) {
			allPlaceIts.addPlaceIt(new PlaceIt(todoArr.get(i), '0', 0), 0);
			allPlaceIts.addPlaceIt(new PlaceIt(ipArr.get(i), '0', 1), 1);
			allPlaceIts.addPlaceIt(new PlaceIt(compArr.get(i), '0', 2), 2);
		}
		
		return allPlaceIts;
	}
}
