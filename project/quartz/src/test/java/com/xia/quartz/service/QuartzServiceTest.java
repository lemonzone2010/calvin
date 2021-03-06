package com.xia.quartz.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.xia.quartz.job.InvokerJobBean;
import com.xia.quartz.model.JobEntity;
import com.xia.quartz.model.JobStatus;
import com.xia.quartz.util.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class QuartzServiceTest {

	@Autowired
	QuartzService quartzService;
	@Autowired
	JobEntityService jobEntityService;

	@Test
	public void testStartClassJob() throws SchedulerException, ClassNotFoundException, InterruptedException,
			NoSuchMethodException {
		// 先保存此任务，再测试
		QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
		JobEntity jobEntity = JobEntityServiceTest.newJobEntity();
		jobEntity.setJobClass("com.xia.quartz.job.DemoSpringJob");

		JobEntityService jobEntityService = ApplicationContextHolder.getBean("jobEntityService");
		jobEntityService.saveJobEntity(jobEntity);
		quartzService.startJob(jobEntity);
		quartzService.startJobImmediatelyOnce(jobEntity);
		Thread.sleep(2000);
	}

	@Test
	public void testStartSpringBeanJob() throws SchedulerException, ClassNotFoundException, InterruptedException,
			NoSuchMethodException {
		// 先保存此任务，再测试
		QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
		JobEntity jobEntity = JobEntityServiceTest.newJobEntity();
		jobEntity.setJobClass("nonQuartzJob");
		jobEntity.setJobMethod("execute");
		jobEntity.setJobClassIsBeanName(true);

		JobEntityService jobEntityService = ApplicationContextHolder.getBean("jobEntityService");
		jobEntityService.saveJobEntity(jobEntity);
		quartzService.startJob(jobEntity);
		quartzService.startJobImmediatelyOnce(jobEntity);
		Thread.sleep(2000);
	}

	@Test
	public void testStartJob() throws SchedulerException, ClassNotFoundException, InterruptedException,
			NoSuchMethodException {
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
	public void testPauseJob() throws SchedulerException, ClassNotFoundException, InterruptedException,
			NoSuchMethodException {
		JobEntity jobEntity = JobEntityServiceTest.newJobEntity();
		jobEntityService.saveJobEntity(jobEntity);

		quartzService.startJob(jobEntity);
		Assert.assertEquals(JobStatus.RUNNING, jobEntity.getStatus());

		Thread.sleep(3000);
		quartzService.pauseJob(jobEntity);

		Assert.assertEquals(JobStatus.PAUSED, jobEntity.getStatus());
		Thread.sleep(2000);
	}
}
