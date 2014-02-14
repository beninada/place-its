package com.wi14.team5.place_its;

import android.R.string;

public class Status {

	int s; //1completed, 2in progress or 3to do
	
	public Status(int s) {
	    this.s = 3;
	}
	
	public void change_to_completed()
	{
		s=1;
	}
	
	public void change_to_in_progress()
	{
		s=2;
	}
	
	public void change_to_to_do()
	{
		s=3;
	}
}
