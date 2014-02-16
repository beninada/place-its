package com.wi14.team5.place_its.lists;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * A fragment that represents a list of Place-its.
 */
public class PlaceitListFragment extends ListFragment {

	public static final String NAMES = "names";
	private ArrayList<String> names;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// get the bundled arguments from the list pager adapter
		Bundle bundle = getArguments();
		names = bundle.getStringArrayList(NAMES);
		
		if (names == null) {
			names = new ArrayList<String>();
		}
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                     inflater.getContext(), android.R.layout.simple_list_item_1, names);
 
        setListAdapter(adapter);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
