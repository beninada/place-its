package com.wi14.team5.place_its;

import java.util.HashMap;;

//Instantiated in Main Activity
public class AllPlaceIts { //TODO implment Subject (for GPS)
	HashMap<String, PlaceIt> placeitsTODO;
	HashMap<String, PlaceIt> placeitsINPROGRESS;
	HashMap<String, PlaceIt> placeitsCOMPLETED;
	
	public AllPlaceIts(){
		placeitsTODO 		= new HashMap<String, PlaceIt>();
		placeitsINPROGRESS 	= new HashMap<String, PlaceIt>();
		placeitsCOMPLETED 	= new HashMap<String, PlaceIt>();
	}
	
	//TODO addPlacit methods called by
	public void addTODO(PlaceIt p){
		placeitsTODO.put(p.getName(), p);
	}
	
	public void addINPROGRESS(){
	
	}
	
	public void addCOMPLETED(){
		
	}
}
