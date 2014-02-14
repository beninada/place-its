package com.wi14.team5.place_its;

import java.util.HashMap;

//Instantiated in Main Activity
public class AllPlaceIts { //TODO implement Subject (for GPS)
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
		if (p.getStatus() == 2)
		{
			placeitsINPROGRESS.remove(p.getName());
		}
		if (p.getStatus() == 1)
		{
			placeitsCOMPLETED.remove(p.getName());
		}
		placeitsTODO.put(p.getName(), p);
		p.setStatus(3);
	}
	
	public void addINPROGRESS(PlaceIt p){
		if (p.getStatus() == 3)
		{
			placeitsTODO.remove(p.getName());
		}
		if (p.getStatus() == 1)
		{
			placeitsCOMPLETED.remove(p.getName());
		}
		placeitsINPROGRESS.put(p.getName(), p);
		p.setStatus(2);
	}
	
	public void addCOMPLETED(PlaceIt p){
		if (p.getStatus() == 2)
		{
			placeitsINPROGRESS.remove(p.getName());
		}
		if (p.getStatus() == 3)
		{
			placeitsTODO.remove(p.getName());
		}
		placeitsCOMPLETED.put(p.getName(), p);
		p.setStatus(1);
	}
	
	
}
