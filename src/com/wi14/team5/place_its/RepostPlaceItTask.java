package com.wi14.team5.place_its;

import java.util.TimerTask;

/**
 * A TimerTask for reposting a place-it.
 */
public class RepostPlaceItTask extends TimerTask {

	private AllPlaceIts api;
	private PlaceIt placeit;
	private MainActivity activity;
	
	public RepostPlaceItTask(AllPlaceIts api, PlaceIt placeit, MainActivity act) {
		this.api = api;
		this.placeit = placeit;
		activity = act;
	}
	
	@Override
	public void run() {
		api.updatePlaceIt(placeit, 0);	
		activity.refreshLists(0);
	}

}
