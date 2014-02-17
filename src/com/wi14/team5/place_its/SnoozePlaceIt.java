package com.wi14.team5.place_its;

import java.util.Calendar;
import java.util.Timer;

public class SnoozePlaceIt extends Timer {

	AllPlaceIts allPlaceIts;
	
	public SnoozePlaceIt(AllPlaceIts allPlaceIts, PlaceIt placeit) {
		this.allPlaceIts = allPlaceIts;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 10); // normally 45 minutes
		this.schedule(new RepostPlaceItTask(allPlaceIts, placeit), cal.getTime());
	}

}
