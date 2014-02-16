package com.wi14.team5.place_its;

import java.util.TimerTask;

public class RepostPlaceItTask extends TimerTask{

	private AllPlaceIts api;
	private PlaceIt placeit;
	
	public RepostPlaceItTask(AllPlaceIts api, PlaceIt placeit) {
		this.api = api;
		this.placeit = placeit;
	}
	
	@Override
	public void run() {
		api.updatePlaceIt(placeit, 0);		
	}

}
