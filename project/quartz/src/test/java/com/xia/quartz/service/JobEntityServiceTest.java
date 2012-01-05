package com.xia.quartz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xia.quartz.model.JobEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JobEntityServiceTest {
	@Autowired
	JobEntityService jobService;

	JobEntity jobEntity = new JobEntity();

	@Test
	@Transactional
	public void addJob() {
		jobEntity = newJobEntity();
		jobService.saveJobEntity(jobEntity);
	}

	public static JobEntity newJobEntity() {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobName("Demo任务_" + System.currentTimeMillis());
		jobEntity.setJobDesc("任务是为了作为演示用");
		jobEntity.setJobCronExpress("0/5 * * * * ?");
		jobEntity.setJobClass("com.xia.quartz.job.DemoJob");
		jobEntity.getProperties().put("qty", "100");
		jobEntity.getProperties().put("qtyName", "100");
		return jobEntity;
	}

	@Test
	@Transactional
	public void removeJob() {
		addJob();
		jobService.removeJobEntity(jobEntity);
	}
}
