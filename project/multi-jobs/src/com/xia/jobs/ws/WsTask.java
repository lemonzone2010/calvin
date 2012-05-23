package com.xia.jobs.ws;

import java.util.concurrent.CountDownLatch;

import com.xia.jobs.Query;
import com.xia.jobs.Response;
import com.xia.jobs.ServiceProvider;
import com.xia.jobs.Task;

public class WsTask implements Task {
	private CountDownLatch jobsSignal;
	private ServiceProvider serviceProvider;
	private Query query;
	private Response result;

	public WsTask(CountDownLatch jobsSignal, ServiceProvider serviceProvider, Query query) {
		super();
		this.jobsSignal = jobsSignal;
		this.serviceProvider = serviceProvider;
		this.query = query;
	}

	public void run() {
		try {
			result = serviceProvider.getResult(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jobsSignal.countDown();
	}

	public Response getResult() {
		return result;
	}

}
