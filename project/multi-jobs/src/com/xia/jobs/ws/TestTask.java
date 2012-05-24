package com.xia.jobs.ws;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.TaskFactory;
import com.xia.jobs.WorkItem;

public class TestTask {
	public static void main(String[] args) {
		Query query = new WsQuery();
		Context<WorkItem> context = new WsContext();
		
		
		TaskFactory<WorkItem> taskFactory = new WsTaskFactory();
		
		System.out.println("OK.");
		taskFactory.exceute(context, query);

		Response<WorkItem> result = taskFactory.getResult();
		System.out.println(result+","+result.getQueryTime());
		
		
		TaskFactory.executor.shutdown();
	}
}
