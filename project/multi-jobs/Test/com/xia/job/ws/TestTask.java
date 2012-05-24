package com.xia.job.ws;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.TaskFactory;
import com.xia.jobs.WorkItem;
import com.xia.jobs.ws.WsContext;
import com.xia.jobs.ws.WsQuery;
import com.xia.jobs.ws.WsTaskFactory;

public class TestTask {
	public static void main(String[] args) {
		Context<WorkItem> context = new WsContext();
		
		Query query = new WsQuery();
		query.parse(null);//从request里解析参数
		
		TaskFactory<WorkItem> taskFactory = new WsTaskFactory();
		
		System.out.println("OK.");
		taskFactory.exceute(context, query);

		Response<WorkItem> result = taskFactory.getResult();
		System.out.println(result+","+result.getQueryTime());
		
		
		TaskFactory.executor.shutdown();
	}
}
