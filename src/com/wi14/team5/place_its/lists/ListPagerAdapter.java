package com.wi14.team5.place_its.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the lists in the app.
 */
public class ListPagerAdapter extends FragmentPagerAdapter {

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PlaceitListFragment();

        Bundle args = new Bundle();
        args.putInt(PlaceitListFragment.ARG_SECTION_NUMBER, i + 1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
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
}

