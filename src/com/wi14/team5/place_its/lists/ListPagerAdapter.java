package com.wi14.team5.place_its.lists;

import java.util.ArrayList;
import com.wi14.team5.place_its.AllPlaceIts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the three Place-it lists.
 * It accesses all of the Place-its
 */
public class ListPagerAdapter extends FragmentPagerAdapter {
	
	private AllPlaceIts allPlaceIts;
	private final static int NUM_OF_LISTS = 3;

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    /**
     * Gets the list corresponding to the parameter list number.
     * 0 -> TO DO
     * 1 -> IN PROGRESS
     * 2 -> COMPLETED
     */
    @Override
    public Fragment getItem(int listNum) {
    	ArrayList<String> names;

    	switch (listNum) {
    		case 0:
    			names = new ArrayList<String>(allPlaceIts.getTODO().keySet());
    		case 1:
    			names = new ArrayList<String>(allPlaceIts.getINPROGRESS().keySet());
    		case 2:
    			names = new ArrayList<String>(allPlaceIts.getCOMPLETED().keySet());
            default:
                names = new ArrayList<String>(0);
    	}
    	
    	Fragment fragment = new PlaceitListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(PlaceitListFragment.LIST_ITEMS, names);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Gets the title for the tab corresponding to the parameter list number.
     */
    @Override
    public CharSequence getPageTitle(int listNum) {
        switch (listNum) {
           case 0:  return "TO DO";
           case 1:  return "IN PROGRESS";
           case 2:  return "COMPLETED";
           default: return "LIST";
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_LISTS;
    }
}
