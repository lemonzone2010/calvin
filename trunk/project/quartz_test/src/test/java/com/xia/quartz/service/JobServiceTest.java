package com.xia.quartz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.service.JobEntityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class JobServiceTest {

	@Autowired
	JobEntityService jobService;

	@Test
	@Transactional
	public void addJob() {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobName("Demo任务1");
		jobEntity.setJobDesc("任务是为了作为演示用");
		jobEntity.setJobCronExpress("0/5 * * * * *");
		jobEntity.getProperties().put("qty", "100");
		jobEntity.getProperties().put("qtyName", "100");

		jobService.addJob(jobEntity);
		
		//jobService.removeJob(jobEntity);
	}
}
