package com.xia.quartz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.util.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JobEntity2JobTest {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		JobDetailImpl im = ApplicationContextHolder.getBean("jobDetail");
		new JobEntity2JobTest().convert();
	}

	@Test
	public void convert() {
		// 先保存此任务，再测试
		QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobName("Demo任务1");
		jobEntity.setJobDesc("任务是为了作为演示用");
		// jobEntity.setJobClass("com.xia.quartz.service.NopJob");
		jobEntity.setJobClass("com.xia.quartz.job.DemoJob");
		// jobEntity.setJobClass("com.xia.quartz.job.JobDetailImpl");
		jobEntity.setJobClassIsBeanName(true);
		jobEntity.setJobCronExpress("0/2 * * * * ?");
		jobEntity.getProperties().put("qty", "100");
		jobEntity.getProperties().put("qtyName", "100");

		try {
			quartzService.startJob(jobEntity);
			// jobEntity.setJobName("test2");

			// quartzService.startJob(jobEntity);
			// quartzService.pauseJob(jobEntity);
			// Thread.sleep(6000);
			// quartzService.resumeJob(jobEntity);
			// Scheduler scheduler = QuartzUtil.getScheduler();
			// scheduler.start();
			// QuartzUtil.startScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
