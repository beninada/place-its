package com.wi14.team5.place_its;

import com.wi14.team5.place_its.lists.ListPagerAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * Fragment Activity that contains the three lists: To Do, In Progress, and
 * Completed. They are displayed in tabs that the user can select/scroll
 * through.
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * A {@link android.support.v4.view.PagerAdapter} that provides fragments
	 * for each of the three lists in the app.
	 */
	ListPagerAdapter lpAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		lpAdapter = new ListPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(lpAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					// When swiping between different app sections, select
					// the corresponding tab.
					actionBar.setSelectedNavigationItem(position);
				}
			});

		// For each list, add a tab to the action bar.
		for (int i = 0; i < lpAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			actionBar.addTab(actionBar.newTab()
					.setText(lpAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
}
