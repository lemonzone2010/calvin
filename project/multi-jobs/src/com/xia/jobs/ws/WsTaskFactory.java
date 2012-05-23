package com.xia.jobs.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xia.jobs.Context;
import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.TaskFactory;

public class WsTaskFactory implements TaskFactory {
	private CountDownLatch jobsSignal;
	private Context context;
	private long startTime;

	public WsTaskFactory(Context context, Query query) {
		this.context = context;
		this.query = query;
		init();
	}

	private Query query;
	private List<WsTask> tasks;



	private void init() {
		jobsSignal = new CountDownLatch(context.getServiceProviders().size());
		tasks = generateTasks();

		// for (Task task : tasks) {
		// task.setJobsSignal(jobsSignal);
		// }
	}

	private List<WsTask> generateTasks() {
		List<WsTask> ret = new ArrayList<WsTask>();
		List<ServiceProvider> serviceProviders = context.getServiceProviders();
		for (ServiceProvider serviceProvider : serviceProviders) {
			WsTask task = new WsTask(jobsSignal, serviceProvider, query);
			ret.add(task);
		}
		return ret;
	}

	public void exceute() {
		startTime=System.currentTimeMillis();
		for (WsTask task : tasks) {
			executor.execute(task);
		}
	}

	public Response getResult() {
		try {
			jobsSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int queryTime=(int) (System.currentTimeMillis()-startTime);
		for (WsTask task : tasks) {
			System.out.println("result:" + task.getResult());
			// return task.getResult();
		}
		System.out.println("Query Time:"+queryTime);
		return null;

	}

}
