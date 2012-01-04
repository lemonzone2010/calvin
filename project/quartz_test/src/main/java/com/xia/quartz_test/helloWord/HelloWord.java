/*以下是2.0的代码
 * 
 * package com.xia.quartz_test.helloWord;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloWord {

	*//**
	 * @param args
	 * @throws Exception
	 *//*
	public static void main(String[] args) throws Exception {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class).withIdentity("helloworldJob", "helloGroup")
				.build();
		 jobDetail.getJobDataMap().put("name", "xiayong");

		Date runTime = DateBuilder.nextGivenSecondDate(null, 10);
		System.out.println(runTime);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("helloTrigger", "helloGroup").startAt(runTime)
				.build();

	Date ft=	scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
		System.out.println("Scheduler started.." + new Date().toLocaleString()+","+ft);
		
		
		jobDetail = JobBuilder.newJob(HelloWorldJob.class).withIdentity("helloworldJob1", "helloGroup")
				.build();
		 jobDetail.getJobDataMap().put("name", "xiayong");
		 jobDetail.getJobDataMap().put("count", 1);
		
		SimpleTrigger trigger1 = TriggerBuilder.newTrigger()
	            .withIdentity("trigger3", "helloGroup")
	            .startAt(runTime)
	            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
	                    .withIntervalInSeconds(10)
	                    .withRepeatCount(10))
	            .build();
		// ft = scheduler.scheduleJob(jobDetail, trigger1);
		CronTrigger triggerCorn=TriggerBuilder.newTrigger().withIdentity("trigger4", "helloGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?")).build();
		ft = scheduler.scheduleJob(jobDetail, triggerCorn);
		 System.out.println(jobDetail.getKey() +
	                " will run at: " + ft +  
	                " and repeat: " + trigger1.getRepeatCount() + 
	                " times, every " + trigger1.getRepeatInterval() / 1000 + " seconds");
	}

}
*/