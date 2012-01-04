package com.xia.quartz.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.model.JobStatus;
import com.xia.quartz.util.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JobStatusTest {
	public static void main(String[] args) throws SchedulerException, ClassNotFoundException, InterruptedException {
		new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		JobDetailImpl im = ApplicationContextHolder.getBean("jobDetail");
		new JobStatusTest().testStartJob();
	}

	@Autowired
	QuartzService quartzService;
	@Autowired
	JobEntityService jobService;

	@Test
	public void testStartJob() throws SchedulerException, ClassNotFoundException, InterruptedException {
		// 先保存此任务，再测试
		QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
		JobEntity jobEntity = JobEntityServiceTest.newJobEntity();

		quartzService.startJob(jobEntity);
		// jobEntity.setJobName("test2");

		// quartzService.startJob(jobEntity);
		// quartzService.pauseJob(jobEntity);
		// Thread.sleep(6000);
		// quartzService.resumeJob(jobEntity);
		// Scheduler scheduler = QuartzUtil.getScheduler();
		// scheduler.start();
		// QuartzUtil.startScheduler();
		Thread.sleep(2000);
	}

	@Test
	public void testPauseJob() throws SchedulerException, ClassNotFoundException, InterruptedException {
		JobEntity jobEntity = JobEntityServiceTest.newJobEntity();
		jobService.addJob(jobEntity);

		quartzService.startJob(jobEntity);
		Assert.assertEquals(JobStatus.RUNNING, jobEntity.getStatus());

		Thread.sleep(3000);
		quartzService.pauseJob(jobEntity);

		Assert.assertEquals(JobStatus.PAUSED, jobEntity.getStatus());
		Thread.sleep(2000);
	}
}
