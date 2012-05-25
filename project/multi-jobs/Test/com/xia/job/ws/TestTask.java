package com.xia.job.ws;

import java.util.HashMap;
import java.util.Map;

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
		Map<String,String[]> requestMap=new HashMap<String, String[]>();
		requestMap.put("category", new String[] {"todo"});
		requestMap.put("category", new String[] {"attention"});
		requestMap.put("owerId", new String[] {"xia"});
		
		Context<WorkItem> context = new WsContext();//得到环境变量配置
		
		Query query = new WsQuery();//查询结构体
		query.parse(requestMap);//从request里解析参数
		
		TaskFactory<WorkItem> taskFactory = new WsTaskFactory();//得到任务工厂
		
		taskFactory.exceute(context, query);//执行任务

		Response<WorkItem> result = taskFactory.getResult();//得到任务结果
		
		
		System.out.println(result+","+result.getQueryTime()+",size:"+result.getSize());
		for (WorkItem item : result.getData()) {
			System.out.println(item);
		}
		
		TaskFactory.executor.shutdown();
	}
}
