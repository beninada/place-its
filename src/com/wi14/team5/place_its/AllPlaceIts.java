package com.wi14.team5.place_its;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

public class AllPlaceIts extends Observable{
	private HashMap<String, PlaceIt> placeitsTODO;
	private HashMap<String, PlaceIt> placeitsINPROGRESS;
	private HashMap<String, PlaceIt> placeitsCOMPLETED;
	
	public AllPlaceIts(){
		this.placeitsTODO 		= new HashMap<String, PlaceIt>();
		this.placeitsINPROGRESS 	= new HashMap<String, PlaceIt>();
		this.placeitsCOMPLETED 	= new HashMap<String, PlaceIt>();
	}
	
	//public void addTODO(PlaceIt p)			{placeitsTODO.put(p.getName(), p);}
	//public void addINPROGRESS(PlaceIt p)	{placeitsINPROGRESS.put(p.getName(), p);}
	//public void addCOMPLETED(PlaceIt p)		{placeitsCOMPLETED.put(p.getName(), p);}

	
	public HashMap<String, PlaceIt> getTODO()		{return this.placeitsTODO;}
	public HashMap<String, PlaceIt> getINPROGRESS()	{return this.placeitsINPROGRESS;}
	public HashMap<String, PlaceIt> getCOMPLETED()	{return this.placeitsCOMPLETED;}

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
