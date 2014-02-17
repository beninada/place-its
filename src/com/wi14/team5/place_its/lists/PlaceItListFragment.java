package com.wi14.team5.place_its.lists;

import java.util.ArrayList;

import com.wi14.team5.place_its.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
	
	private static boolean hasBeenCreated = false;
	
	private OnAllPlaceItsModifiedListener mCallBack;
	
	public interface OnAllPlaceItsModifiedListener {
		public void onPlaceItsModified(String name, int curr, int dest, boolean remove);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallBack = (OnAllPlaceItsModifiedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnAllPlaceItsModifiedListener.");
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		// get the list item names from the list pager adapter
		Bundle bundle = getArguments();
		names = bundle.getStringArrayList(NAMES);
		listNum = bundle.getInt(LIST_NUM);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!hasBeenCreated) {
            namesAdapter = new ArrayAdapter<String>(inflater.getContext(),
                                         android.R.layout.simple_list_item_1, names);
        } else {
            if (names.size() > 0) {
                namesAdapter.addAll(names);
            }
        }

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
		// don't dispatch context selection events to non-visible fragments
		if (getUserVisibleHint()) {
            AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
            String selection = namesAdapter.getItem(info.position);
            int id = item.getItemId();

            if (id != R.id.menu_item_cancel) {
                namesAdapter.remove(selection); // remove selection from our list

                switch (id) {
                    case R.id.menu_item_todo:
                    	// call back to allPlaceIts in MainActivity
                    	mCallBack.onPlaceItsModified(selection, listNum, 0, false);
                        break;
                    case R.id.menu_item_inprogress:
                    	mCallBack.onPlaceItsModified(selection, listNum, 1, false);
                        break;
                    case R.id.menu_item_completed:
                    	mCallBack.onPlaceItsModified(selection, listNum, 2, false);
                        break;
                    case R.id.menu_item_delete:
                    	mCallBack.onPlaceItsModified(selection, listNum, -1, true);
                        break;
                }
                
                namesAdapter.notifyDataSetChanged();
            }

            return true;
		}
        
        return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Gets the array adapter for this list fragment.
	 */
	public ArrayAdapter<String> getNamesAdapter() {
		return namesAdapter;
	}
}
