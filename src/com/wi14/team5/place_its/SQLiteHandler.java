package com.wi14.team5.place_its;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Provides helper methods for working with the SQLite database.
 */
public class SQLiteHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "placits.db";
    private static final String TABLE_PLACEITS = "placeits";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LAT	= "latitude";
    private static final String KEY_LNG = "longitude";
    private static final String KEY_SNIP = "description";
    private static final String KEY_REC = "recurrence";
    private static final String KEY_LIST = "list";
	
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("Created Database", "...");
    }

	public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACEITS_TABLE = "CREATE TABLE " + TABLE_PLACEITS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " Title,"  
               	+ KEY_LAT + " LAT," + KEY_LNG + " LNG,"  
               	+ KEY_SNIP + " SNIP," + KEY_REC + " REC,"
               	+ KEY_LIST + " List" + ")";

        db.execSQL(CREATE_PLACEITS_TABLE);

        Log.i("Created Table", "...");
    }

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEITS);
        onCreate(db);
    }

	public void addAllPlaceIts(AllPlaceIts placeits) {
		SQLiteDatabase db 		= this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACEITS);
		
        String CREATE_PLACEITS_TABLE = "CREATE TABLE " + TABLE_PLACEITS + "("
        		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " Title,"  
        		+ KEY_LAT + " LAT," + KEY_LNG + " LNG,"  
        		+ KEY_SNIP + " SNIP," + KEY_REC + " REC,"
        		+ KEY_LIST + " List" + ")";

        db.execSQL(CREATE_PLACEITS_TABLE);
        
        for(PlaceIt p : placeits.getTODO().values()) {
        	ContentValues values 	= new ContentValues();

        	values.put(KEY_TITLE, p.getName());
        	values.put(KEY_LAT, p.getLat());
        	values.put(KEY_LNG, p.getLng());
        	values.put(KEY_SNIP, p.getDescription());
        	values.put(KEY_REC, p.getRecurrence());
        	values.put(KEY_LIST, p.getStatus());

        	db.insert(TABLE_PLACEITS, null, values);
        }

        for(PlaceIt p : placeits.getINPROGRESS().values()) {
        	ContentValues values 	= new ContentValues();
        	
        	values.put(KEY_TITLE, p.getName());
        	values.put(KEY_LAT, p.getLat());
        	values.put(KEY_LNG, p.getLng());;
        	values.put(KEY_SNIP, p.getDescription());
        	values.put(KEY_REC, p.getRecurrence());
        	values.put(KEY_LIST, p.getStatus());
        	
        	db.insert(TABLE_PLACEITS, null, values);
        }
        for(PlaceIt p : placeits.getCOMPLETED().values()) {
        	ContentValues values 	= new ContentValues();
        	
        	values.put(KEY_TITLE, p.getName());
        	values.put(KEY_LAT, p.getLat());
        	values.put(KEY_LNG, p.getLng());
        	values.put(KEY_SNIP, p.getDescription());
        	values.put(KEY_REC, p.getRecurrence());
        	values.put(KEY_LIST, p.getStatus());
        	
        	db.insert(TABLE_PLACEITS, null, values);
        }
        
        Log.i("All records written to database", "------------------->");
        db.close();
	}
	
	public ArrayList<HashMap<String, PlaceIt>> getAllPlaceIts() {
		ArrayList<HashMap<String,PlaceIt>> all 	= new ArrayList<HashMap<String,PlaceIt>>(3);
		HashMap<String, PlaceIt> todo = new HashMap<String, PlaceIt>();
		HashMap<String, PlaceIt> inprogress = new HashMap<String, PlaceIt>();
		HashMap<String, PlaceIt> completed = new HashMap<String, PlaceIt>();
		
	    String selectQuery = "SELECT  * FROM " + TABLE_PLACEITS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);

	    if (cursor.moveToFirst()) {
	        do {
	        	int status = Integer.parseInt(cursor.getString(6));
        		String title = cursor.getString(1);
        		double lat = Double.parseDouble(cursor.getString(2));
        		double lng = Double.parseDouble(cursor.getString(3));
        		String snippet = cursor.getString(4);
        		int reccurence = Integer.parseInt(cursor.getString(5));
        		
        		PlaceIt placeit	= new PlaceIt(title, lat, lng, snippet, (byte)reccurence, status);
        		
	        	if(status == 0)	{todo.put(placeit.getName(), placeit);}
	        	if(status == 1)	{inprogress.put(placeit.getName(), placeit);}
	        	if(status == 2)	{completed.put(placeit.getName(), placeit);}
	        } while (cursor.moveToNext());
	    }
	    
	    all.add(todo);
	    all.add(inprogress);
	    all.add(completed);

	    Log.i("All records read from database", "<-------------------");
	    return all;
	}
	
	public int getPlaceItCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLACEITS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}
