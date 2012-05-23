package com.xia.jobs.workitem;

import com.xia.jobs.WorkItem;

public class Todo implements WorkItem{

	public WorkItem covert(Object responseOneData) {
		// TODO Auto-generated method stub
		System.out.println("TODO:"+responseOneData);
		return this;
	}

}
