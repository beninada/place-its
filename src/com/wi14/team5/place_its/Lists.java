package com.wi14.team5.place_its;

import java.util.List;

public class Lists {

	List<Task> all_list;
	List<Task> completed_list; //the class Task needs to be created, as this is a list of "tasks"
	List<Task> in_progress_list;
	List<Task> to_do_list;
	
	public Lists()
	{
		
	}
	
	public void update_lists() //this method has to be called every time a task's status is modified or if it is deleted, so that the lists are up to date at all time
	{
		for(Task task: all_list)
		{
			if (task.Status.s == 1)
			{
				completed_list.add(task);
			}
			if (task.Status.s == 2)
			{
				in_progress_list.add(task);
			}
			if (task.Status.s == 3)
			{
				to_do_list.add(task);
			}
		}
	}
}
