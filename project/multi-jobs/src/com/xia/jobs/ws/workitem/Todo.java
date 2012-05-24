package com.xia.jobs.ws.workitem;

import com.xia.jobs.WorkItem;

public class Todo implements WorkItem{

	public WorkItem convert(Object responseOneData) {
		// TODO Auto-generated method stub
		System.out.println("TODO:"+responseOneData);
		return this;
	}

	public Object reverse2Params() {
		// TODO Auto-generated method stub
		return null;
	}

}
