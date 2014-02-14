package com.wi14.team5.place_its.lists;

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

	public static final String ARG_SECTION_NUMBER = "section_number";

	private String[] todoListItems;
	private String[] inprogressListItems;
	private String[] completedListItems;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        todoListItems = new String[] {
                "Pick up book", "Buy a new pencil", "Water flowers",
                "Finish app", "Turn in CSE 110 homework", "Deliver pizza",
                "Drink das boot", "Wash car"
        };
        inprogressListItems = new String[] {
                "Pick up book", "Buy a new pencil", "Water flowers",
                "Finish app", "Turn in CSE 110 homework", "Deliver pizza",
                "Drink das boot", "Wash car"
        };
        completedListItems = new String[] {
                "Pick up book", "Buy a new pencil", "Water flowers",
                "Finish app", "Turn in CSE 110 homework", "Deliver pizza",
                "Drink das boot", "Wash car"
        };
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter =
        				new ArrayAdapter<String>(
                        inflater.getContext(), android.R.layout.simple_list_item_1,
                        todoListItems);

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
