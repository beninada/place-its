package com.wi14.team5.place_its;

import java.lang.String;

/**
 * A class that represents a Place-it.
 * Contains all information needed to place a Marker on a GoogleMap.
 */
public class PlaceIt {
	private String name;
	private double lat;
	private double lng;
	private String description;
	private byte recurrence;
	private int status;
	
	public PlaceIt(String name, double lat, double lng,
			String description, byte recurrence, int status) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.description = description;
		this.recurrence	= recurrence;
		this.status = status;
	}
	
	// GETTERS ================================================================
	public String getName()	{
		return this.name;
	}

	public double getLat() {
		return this.lat;
	}

	public double getLng() {
		return this.lng;
	}

	public String getDescription() {
		return this.description;
	}

	public byte getRecurrence() {
		return this.recurrence;
	}

	public int getStatus() {
		return this.status;
	}
	
	// SETTERS ================================================================
	public void setName(String name) {
		this.name = name;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRecurrence(byte recurrence) {
		this.recurrence = recurrence;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
