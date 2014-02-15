package com.wi14.team5.place_its.lists;

import java.util.ArrayList;
import java.util.HashMap;

import com.wi14.team5.place_its.AllPlaceIts;
import com.wi14.team5.place_its.PlaceIt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A FragmentPagerAdapter that returns a fragment corresponding to
 * one of the three Place-it lists.
 */
public class ListPagerAdapter extends FragmentPagerAdapter {
	
	private final static int NUM_OF_LISTS = 3;
	
	private HashMap<String, PlaceIt> todo;
	private HashMap<String, PlaceIt> inProgress;
	private HashMap<String, PlaceIt> completed;

	/**
	 * Prepares the adapter for building the fragment.
	 * @param fm The fragment manager for the main activity.
	 * @param allPlaceIts The PlaceIts to fill each list up with,
	 *		null if there are no PlaceIts to add.
	 */
    public ListPagerAdapter(FragmentManager fm, AllPlaceIts allPlaceIts) {
        super(fm);
        
        if (allPlaceIts != null) {
            todo = allPlaceIts.getTODO();
            inProgress = allPlaceIts.getINPROGRESS();
            completed = allPlaceIts.getCOMPLETED();
        } else {
        	todo = new HashMap<String, PlaceIt>();
        	inProgress = new HashMap<String, PlaceIt>();
        	completed = new HashMap<String, PlaceIt>();
        }
    }

    /**
     * Instantiates the fragment corresponding to the parameter list number.
     * @param listNum the list number of the desired list.
     * @return the desired PlaceitListFragment.
     */
    @Override
    public Fragment getItem(int listNum) {
    	ArrayList<String> names;
    	
    	switch (listNum) {
    		case 0:
                names = new ArrayList<String>(todo.keySet());
    		case 1:
                names = new ArrayList<String>(inProgress.keySet());
    		case 2:
                names = new ArrayList<String>(completed.keySet());
    		default:
    			names = null;
    	}
    	
    	return buildPLF(names);
    }

  	/**
  	 * Build a PlaceitListFragment with the parameter names.
  	 * @param names the names of each item in the list.
  	 * @return the PlaceitListFragment for the desired list.
  	 */
    public Fragment buildPLF(ArrayList<String> names) {
    	Fragment listFragment = new PlaceitListFragment();

    	// bundle up the names to build the appropriate fragment
        Bundle args = new Bundle();
        args.putStringArrayList(PlaceitListFragment.NAMES, names);
        listFragment.setArguments(args);

        return listFragment;
    }

    /**
     * Gets the title for the tab corresponding to the parameter list number.
     */
    @Override
    public CharSequence getPageTitle(int listNum) {
        switch (listNum) {
           case 0:
        	   return "TO DO";
           case 1:
        	   return "IN PROGRESS";
           case 2:
        	   return "COMPLETED";
           default:
        	   return "LIST";
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_LISTS;
    }
}
