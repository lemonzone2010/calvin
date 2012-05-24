package com.xia.job.ws.workitem;

import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.workitem.CategoryEnum;

public class TestWorkItem {
	public static void main(String[] args) {
		WorkItem covert = CategoryEnum.TODO.covert("xxx");
		System.out.println(covert);
	}

}
