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
		
		final Intent i = new Intent(MainActivity.this, AddPlaceitActivity.class);

	    final Button button = (Button) findViewById(R.id.buttonCancel);
	    
	    button.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            startActivity(i);
	        }
	    });
	    
	}
	
	public void buttonClick(View v) {
		
	}
	
	

}
