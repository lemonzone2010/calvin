package com.xia.jobs.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.TaskFactory;
import com.xia.jobs.WorkItem;
import org.apache.log4j.Logger;


public class WsTaskFactory implements TaskFactory<WorkItem> {
	private final Logger logger=Logger.getLogger(getClass());
	private CountDownLatch jobsSignal;
	private long startTime;

	private List<WsTask> tasks;

	private void init(Context<WorkItem> context, Query query) {
		jobsSignal = new CountDownLatch(context.getServiceProviders(query).size());
		tasks = generateTasks(context, query);
		startTime = System.currentTimeMillis();
	}

	private List<WsTask> generateTasks(Context<WorkItem> context, Query query) {
		List<WsTask> ret = new ArrayList<WsTask>();
		List<ServiceProvider<WorkItem>> serviceProviders = context.getServiceProviders(query);
		for (ServiceProvider<WorkItem> serviceProvider : serviceProviders) {
			WsTask task = new WsTask(jobsSignal, serviceProvider, query);
			ret.add(task);
		}
		return ret;
	}

	public TaskFactory<WorkItem> exceute(Context<WorkItem> context, Query query) {
		logger.info("start execute tasks");
		init(context, query);

		for (WsTask task : tasks) {
			executor.execute(task);
		}

		return this;
	}

	public Response<WorkItem> getResult() {
		try {
			jobsSignal.await();//等待所有任务的完成
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int queryTime = (int) (System.currentTimeMillis() - startTime);
		WorkResponse response = new WorkResponse();
		response.setQueryTime(queryTime);

		for (WsTask task : tasks) {
			// TODO process the result
			Response<WorkItem> result = task.getResult();
			if (null == result)
				continue;

			response.setStatus(result.getStatus());
			response.addData(result.getData());

		}
		logger.info("Query Time:" + queryTime);
		return response;
	}

}
