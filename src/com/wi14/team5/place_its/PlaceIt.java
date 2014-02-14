package com.wi14.team5.place_its;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.lang.String;

public class PlaceIt {
	private Marker marker;
	private char recurrence;
	private int status;
	
	public PlaceIt(Marker marker, char recurrence){
		this.marker     = marker;
		this.recurrence = recurrence;
		this.status = 3;
	}
	
	public void writeOut(){
	}
	
	public void readIn(){	
	}
	
	public String getName()							{return this.marker.getTitle();}
	public LatLng getPosition()						{return this.marker.getPosition();}
	public String getDescription()					{return this.marker.getSnippet();}
	public Marker getMarker()						{return this.marker;}
	public char getRecurrence()						{return this.recurrence;}
	public int getStatus()							{return this.status;}
	
	public void setName(String name)				{this.marker.setTitle(name);}
	public void setPosition(LatLng position)		{this.marker.setPosition(position);}
	public void setDescription(String description)	{this.marker.setSnippet(description);}
	public void setMarker(Marker marker)			{this.marker = marker;}
	public void setRecurrence(char recurrence)		{this.recurrence = recurrence;}
	public void setStatus(int status)				{this.status = status;}

}
