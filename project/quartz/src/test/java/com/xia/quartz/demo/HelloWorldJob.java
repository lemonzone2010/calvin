package com.xia.quartz.demo;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloWorldJob implements Job {

	public HelloWorldJob() {
		// TODO Auto-generated constructor stub
	}

	private int _counter = 1;

	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDetail jobDetail = context.getJobDetail();
		String jobName = jobDetail.getKey().getName();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		Object name = jobDataMap.get("name");
		if (jobDataMap.containsKey("count")) {
			int count = jobDataMap.getInt("count");
			count = count + 1;
			_counter++;
			jobDataMap.put("count", count);
			System.out.println(count + "," + _counter);
		}

		System.out.println("Hello," + name + ",in job" + jobName + ",@" + new Date());

	}

}