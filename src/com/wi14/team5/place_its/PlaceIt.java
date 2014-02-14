package com.wi14.team5.place_its;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.lang.String;

public class PlaceIt {
	private String name;
	private LatLng position;
	private String description;
	private Marker marker;
	
	public PlaceIt(String name, LatLng position, String description, Marker marker){
		this.name        = name;
		this.position    = position;
		this.description = description;
		this.marker      = marker;
	}
	
	public String getName(){
		return this.name;
	}
	
	public LatLng getPosition(){
		return this.position;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public Marker getMarker(){
		return this.marker;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPosition(LatLng position){
		this.position = position;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setMarker(Marker marker){
		this.marker = marker;
	}
}
