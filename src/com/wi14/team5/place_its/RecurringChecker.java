package com.wi14.team5.place_its;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class RecurringChecker extends Timer{
	TimerTask timer;
	AllPlaceIts allPlaceIts;
	
	public RecurringChecker(AllPlaceIts allPlaceIts) {
		this.allPlaceIts = allPlaceIts;
		if(allPlaceIts.getCOMPLETED() != null && !allPlaceIts.getCOMPLETED().isEmpty()) { 
			for(PlaceIt placeit : allPlaceIts.getCOMPLETED().values()) {
				checkRecurrance(placeit);
			}
		}
		if(allPlaceIts.getINPROGRESS() != null && !allPlaceIts.getINPROGRESS().isEmpty()) {
			for(PlaceIt placeit : allPlaceIts.getINPROGRESS().values()) {
				checkRecurrance(placeit);
			}
		}
			
	}
	
	public void checkRecurrance(PlaceIt placeit) {
		if(placeit.getRecurrence() != 0) {
			if(placeit.getRecurrence() == (byte)0x80) { //Test mode, repost by the minute
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 1);
				this.schedule(new RepostPlaceItTask(allPlaceIts, placeit), cal.getTime());
			} else {
				findNextDay(placeit);
			}
		}
		
	}

	public void findNextDay(PlaceIt placeit) {
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(cal.DAY_OF_WEEK);
		boolean timerSet = false;
		int i = 0;
		while(!timerSet) {
			i++;
			dayOfWeek++;
			if(dayOfWeek >= 8) {
				dayOfWeek = 1;
			}
			if(((byte)placeit.getRecurrence() & (byte)0x40) > 0 && dayOfWeek == Calendar.SUNDAY) { //Sunday
				timerSet = true;	
			} else if(((byte)placeit.getRecurrence() & (byte)0x20) > 0 && dayOfWeek == Calendar.MONDAY) {//Monday
				timerSet = true;
			} else if(((byte)placeit.getRecurrence() & (byte)0x10) > 0 && dayOfWeek == Calendar.TUESDAY) {//Tues
				timerSet = true;
			} else if(((byte)placeit.getRecurrence() & (byte)0x08) > 0 && dayOfWeek == Calendar.WEDNESDAY) {//Wed
				timerSet = true;
			} else if(((byte)placeit.getRecurrence() & (byte)0x04) > 0 && dayOfWeek == Calendar.THURSDAY) {//Thurs
				timerSet = true;
			} else if(((byte)placeit.getRecurrence() & (byte)0x02) > 0 && dayOfWeek == Calendar.FRIDAY) {//Fri
				timerSet = true;
			} else if(((byte)placeit.getRecurrence() & (byte)0x01) > 0 && dayOfWeek == Calendar.SATURDAY) {//Sat
				timerSet = true;
			}
			if(timerSet) {
				cal.add(Calendar.DATE, i);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				this.schedule(new RepostPlaceItTask(allPlaceIts, placeit), cal.getTime());
			}
		}
	}
}
