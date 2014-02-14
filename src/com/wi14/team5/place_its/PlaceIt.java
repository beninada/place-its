package com.wi14.team5.place_its;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.lang.String;

public class PlaceIt {
	private Marker marker;
	private char reccurance;				//snippit, title, position
	private int status; //1completed, 2in progress or 3to do
	
	public PlaceIt(Marker marker, char reccurance){
		this.marker     = marker;
		this.reccurance = reccurance;
		this.status = 3;
	}
	
	public void writeOut(){
		//TODO write object to database
	}
	
	public void readIn(){
		//TODO read object from database
	}
	
	public String getName(){
		return this.marker.getTitle();
	}
	
	public LatLng getPosition(){
		return this.marker.getPosition();
	}
	
	public String getDescription(){
		return this.marker.getSnippet();
	}
	
	public Marker getMarker(){
		return this.marker;
	}
	
	public void setName(String name){
		this.marker.setTitle(name);
	}
	
	public void setPosition(LatLng position){
		this.marker.setPosition(position);
	}
	
	public void setDescription(String description){
		this.marker.setSnippet(description);
	}
	
	public void setMarker(Marker marker){
		this.marker = marker;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int number){
		this.status = number;
	}
}
