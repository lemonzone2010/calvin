package com.xia.jobs.ws;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.TaskFactory;

public class TestTask {
	public static void main(String[] args) {
		Query query=new WsQuery();
		Context context=new Context();
		TaskFactory taskFactory=new WsTaskFactory(context, query);
		System.out.println("OK.");
		taskFactory.exceute();
		
		Object result = taskFactory.getResult();
		System.out.println(result);

	}
}
