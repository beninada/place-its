package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION 	= 1;
    private static final String DATABASE_NAME 	= "placits.db";
    private static final String TABLE_PLACEITS 	= "placeits";
    private static final String KEY_ID 			= "id";
    private static final String KEY_TITLE 		= "title";
    private static final String KEY_LAT 		= "latitude";
    private static final String KEY_LNG 		= "longitude";
    private static final String KEY_SNIP 		= "description";
    private static final String KEY_REC 		= "recurrence";
    private static final String KEY_LIST		= "list";
	
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Created Database", "...");
    }

	public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACEITS_TABLE 			= "CREATE TABLE " + TABLE_PLACEITS + "("
                								+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE 
                								+ " LAT," + KEY_LAT + " LNG" + KEY_LNG 
                								+ " SNIP," + KEY_SNIP + " REC" + KEY_REC
                								+ " List" + KEY_LIST + ")";
        db.execSQL(CREATE_PLACEITS_TABLE);
        Log.d("Created Table", "...");
    }

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEITS);
        onCreate(db);
    }
	
	public void addPlaceIt(PlaceIt p) {
		SQLiteDatabase db 		= this.getWritableDatabase();
        ContentValues values 	= new ContentValues();
        
        values.put(KEY_TITLE, p.getName());
        values.put(KEY_LAT, p.getPosition().latitude);
        values.put(KEY_LNG, p.getPosition().longitude);
        values.put(KEY_SNIP, p.getDescription());
        values.put(KEY_REC, (int)p.getRecurrence());
        values.put(KEY_LIST, p.getStatus());
 
        db.insert(TABLE_PLACEITS, null, values);
        db.close();
	}
	
	public ArrayList<HashMap<String, PlaceIt>> getAllPlaceIts(GoogleMap map) {
		ArrayList<HashMap<String,PlaceIt>> all 	= new ArrayList<HashMap<String,PlaceIt>>(3);
		HashMap<String, PlaceIt> todo 			= new HashMap<String, PlaceIt>();
		HashMap<String, PlaceIt> inprogress 	= new HashMap<String, PlaceIt>();
		HashMap<String, PlaceIt> completed 		= new HashMap<String, PlaceIt>();
		
	    String selectQuery = "SELECT  * FROM " + TABLE_PLACEITS;
	 
	    SQLiteDatabase db 	= this.getWritableDatabase();
	    Cursor cursor 		= db.rawQuery(selectQuery, null);

	    if (cursor.moveToFirst()) {
	        do {
	        	int status 		= Integer.parseInt(cursor.getString(6));
        		String title 	= cursor.getString(1);
        		double lat 		= Double.parseDouble(cursor.getString(2));
        		double lng 		= Double.parseDouble(cursor.getString(3));
        		String snippet 	= cursor.getString(4);
        		int reccurence 	= Integer.parseInt(cursor.getString(5));
        		
        		Marker marker 	= map.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(title)
								.snippet(snippet));
        		PlaceIt placeit	= new PlaceIt(marker, (byte)reccurence, status);
        		
	        	if(status == 0)	{todo.put(placeit.getName(), placeit);}
	        	if(status == 1)	{inprogress.put(placeit.getName(), placeit);}
	        	if(status == 2)	{completed.put(placeit.getName(), placeit);}
	        } while (cursor.moveToNext());
	    }
	    all.add(todo);
	    all.add(inprogress);
	    all.add(completed);
	    return all;
	}
	
	public int getPlaceItCount() {
        String countQuery 	= "SELECT  * FROM " + TABLE_PLACEITS;
        SQLiteDatabase db 	= this.getReadableDatabase();
        Cursor cursor 		= db.rawQuery(countQuery, null);
        int count 			= cursor.getCount();
        cursor.close();

        return count;
    }
}
