package com.xia.quartz.service;

import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.util.QuartzUtil;

public class JobEntity2JobTest {
	public static void main(String[] args) {
		new JobEntity2JobTest().convert();
	}

	@Test
	public void convert() {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobName("Demo任务1");
		jobEntity.setJobDesc("任务是为了作为演示用");
		jobEntity.setJobClass("com.xia.quartz.service.NopJob");
		jobEntity.setJobCronExpress("0/2 * * * * ?");
		jobEntity.getProperties().put("qty", "100");
		jobEntity.getProperties().put("qtyName", "100");

		try {
			QuartzUtil.startJob(jobEntity);
			jobEntity.setJobName("test2");
		
			QuartzUtil.startJob(jobEntity);
			QuartzUtil.pauseJob(jobEntity);
			Thread.sleep(6000);
			QuartzUtil.resumeJob(jobEntity);
			// Scheduler scheduler = QuartzUtil.getScheduler();
			// scheduler.start();
			// QuartzUtil.startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
