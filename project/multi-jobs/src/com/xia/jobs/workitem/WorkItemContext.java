package com.xia.jobs.workitem;

import com.xia.jobs.WorkItem;

public class WorkItemContext {
	private WorkItem workItem;

	public WorkItemContext(WorkItem workItem) {
		this.workItem = workItem;
	}

	public WorkItem covert(Object responseOneData) {
		return workItem.covert(responseOneData);
	}

}
