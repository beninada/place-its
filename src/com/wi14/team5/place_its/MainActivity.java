package com.wi14.team5.place_its;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Intent addIntent = new Intent(MainActivity.this, AddPlaceitActivity.class);
		final Intent moreIntent = new Intent(MainActivity.this, MoreInfoActivity.class);
		final Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);

	    final Button add = (Button) findViewById(R.id.buttonCancel);
	    final Button more = (Button) findViewById(R.id.buttonMore);
	    final Button map = (Button) findViewById(R.id.buttonMap);

	    
	    add.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            startActivity(addIntent);
	        }
	    });
	    more.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            startActivity(moreIntent);
	        }
	    });
	    map.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            startActivity(mapIntent);
	        }
	    });
	}
	
	public void buttonClick(View v) {
		
	}
	
	

}
