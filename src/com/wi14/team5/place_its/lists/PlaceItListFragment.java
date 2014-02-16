package com.wi14.team5.place_its.lists;

import java.util.ArrayList;

import com.wi14.team5.place_its.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A fragment that represents a list of Place-its.
 */
public class PlaceItListFragment extends ListFragment {

	private ArrayList<String> names;
	private ArrayAdapter<String> namesAdapter;
	private int listNum;
	
	public static final String NAMES = "names";
	public static final String NAME = "name";
	public static final String LIST_NUM = "list_num";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// get the list item names from the list pager adapter
		Bundle bundle = getArguments();
		names = bundle.getStringArrayList(NAMES);
		listNum = bundle.getInt(LIST_NUM);
		
		if ( (names.size() == 0) || (names == null) ) {
			Log.d("Bundled ArrayList sent to PlaceitListFragment has no names or is null.", "...");
			names = new ArrayList<String>();
		}
		
        namesAdapter = new ArrayAdapter<String>(inflater.getContext(),
      	                           android.R.layout.simple_list_item_1, names);
 
        setListAdapter(namesAdapter);
        
        // register the entire list view to automatically register the list items
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView lv = (ListView) v.findViewById(android.R.id.list);
        registerForContextMenu(lv);
        
        return v;
    }
	
	/**
	 * Inflates the menu resource and uses it to populate the context menu.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater().inflate(R.menu.modify_list_context, menu);
	}
	
	/**
	 * Handles context menu selections.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		String selection = namesAdapter.getItem(info.position);		// the context menu selection
		int id = item.getItemId();

		if (id != R.id.menu_item_cancel) {
            namesAdapter.remove(selection);
            namesAdapter.notifyDataSetChanged();

            switch (id) {
                case R.id.menu_item_todo:
                    break;
                case R.id.menu_item_inprogress:
                    break;
                case R.id.menu_item_completed:
                    break;
                case R.id.menu_item_delete:
                    break;
            }
		}
        
        return super.onOptionsItemSelected(item);
	}
	
	public ArrayAdapter<String> getNamesAdapter() {
		return namesAdapter;
	}
}
