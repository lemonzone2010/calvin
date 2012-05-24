package com.xia.jobs.ws.workitem;

import com.xia.jobs.WorkItem;

public class TestWorkItem {
	public static void main(String[] args) {
		WorkItem covert = CategoryEnum.TODO.covert("xxx");
		System.out.println(covert);
	}

}
