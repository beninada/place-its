package com.wi14.team5.place_its;

import java.util.HashMap;
import java.util.Observable;

import android.util.Log;

public class AllPlaceIts extends Observable{
	private HashMap<String, PlaceIt> placeitsTODO;
	private HashMap<String, PlaceIt> placeitsINPROGRESS;
	private HashMap<String, PlaceIt> placeitsCOMPLETED;
	
	public AllPlaceIts(){
		this.placeitsTODO 		= new HashMap<String, PlaceIt>();
		this.placeitsINPROGRESS = new HashMap<String, PlaceIt>();
		this.placeitsCOMPLETED 	= new HashMap<String, PlaceIt>();
	}

	public AllPlaceIts(HashMap<String, PlaceIt> t, HashMap<String, PlaceIt> i, HashMap<String, PlaceIt> c){
		this.placeitsTODO 		= t;
		this.placeitsINPROGRESS = i;
		this.placeitsCOMPLETED 	= c;
	}
	
	public void addPlaceIt(PlaceIt p, int status){
		if(status == 0)	{placeitsTODO.put(p.getName(), p);}
		if(status == 1)	{placeitsINPROGRESS.put(p.getName(), p);}
		if(status == 2)	{placeitsCOMPLETED.put(p.getName(), p);}
	}
	
	public void removePlaceIt(String name, int status) {
		if(status == 0) {placeitsTODO.remove(name);}
		if(status == 1) {placeitsINPROGRESS.remove(name);}
		if(status == 2) {placeitsCOMPLETED.remove(name);}
	}

	public void updatePlaceItByName(String name, int curr, int dest) {
		PlaceIt p = null;
		if(curr == 0) { p = placeitsTODO.remove(name);}
		if(curr == 1) { p = placeitsINPROGRESS.remove(name);}
		if(curr == 2) { p = placeitsCOMPLETED.remove(name);}
		
		p.setStatus(dest);
		
		switch (dest) {
            case 0:
            	placeitsTODO.put(name, p);
                break;
            case 1:
            	placeitsINPROGRESS.put(name, p);
                break;
            case 2:
            	placeitsCOMPLETED.put(name, p);
                break;
		}
	}
	
	public void updatePlaceIt(PlaceIt p, int toStatus){
		if(p.getStatus() == 0) {placeitsTODO.remove(p.getName());}
		if(p.getStatus() == 1) {placeitsINPROGRESS.remove(p.getName());}
		if(p.getStatus() == 2) {placeitsINPROGRESS.remove(p.getName());}
		
		p.setStatus(toStatus);
		
		if(toStatus == 0)		{placeitsTODO.put(p.getName(), p);}
		if(toStatus == 1)		{placeitsINPROGRESS.put(p.getName(), p);}
		if(toStatus == 2)		{placeitsCOMPLETED.put(p.getName(), p);}
	}

	public HashMap<String, PlaceIt> getTODO()		{return this.placeitsTODO;}
	public HashMap<String, PlaceIt> getINPROGRESS()	{return this.placeitsINPROGRESS;}
	public HashMap<String, PlaceIt> getCOMPLETED()	{return this.placeitsCOMPLETED;}
}
