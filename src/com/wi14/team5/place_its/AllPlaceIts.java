package com.wi14.team5.place_its;

import java.util.HashMap;
import java.util.Observable;

//Instantiated in Main Activity
public class AllPlaceIts extends Observable{
	private HashMap<String, PlaceIt> placeitsTODO;
	private HashMap<String, PlaceIt> placeitsINPROGRESS;
	private HashMap<String, PlaceIt> placeitsCOMPLETED;
	
	public AllPlaceIts(){
		placeitsTODO 		= new HashMap<String, PlaceIt>();
		placeitsINPROGRESS 	= new HashMap<String, PlaceIt>();
		placeitsCOMPLETED 	= new HashMap<String, PlaceIt>();
	}
	
	public void addTODO(PlaceIt p)			{placeitsTODO.put(p.getName(), p);}
	public void addINPROGRESS(PlaceIt p)	{placeitsINPROGRESS.put(p.getName(), p);}
	public void addCOMPLETED(PlaceIt p)		{placeitsCOMPLETED.put(p.getName(), p);}
	
	public void writeOut(){
		
	}
	
	public HashMap<String, PlaceIt> getTODO()		{return this.placeitsTODO;}
	public HashMap<String, PlaceIt> getINPROGRESS()	{return this.placeitsINPROGRESS;}
	public HashMap<String, PlaceIt> getCOMPLETED()	{return this.placeitsCOMPLETED;}
	
}
