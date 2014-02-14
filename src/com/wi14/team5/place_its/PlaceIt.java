package com.wi14.team5.place_its;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import java.lang.String;

public class PlaceIt {
	private Marker marker;
	private char recurrence;
	private int listNum;
	
	public PlaceIt(Marker marker, char recurrence, int listNum){
		this.marker     = marker;
		this.recurrence = recurrence;
		this.listNum 	= listNum;
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
	public int getListNum()							{return this.listNum;}
	
	public void setName(String name)				{this.marker.setTitle(name);}
	public void setPosition(LatLng position)		{this.marker.setPosition(position);}
	public void setDescription(String description)	{this.marker.setSnippet(description);}
	public void setMarker(Marker marker)			{this.marker = marker;}
	public void setRecurrence(char reccurence)		{this.recurrence = recurrence;}
	public void setListNum(int listNum)				{this.listNum = listNum;}
}
