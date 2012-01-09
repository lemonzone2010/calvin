package com.xia.quartz.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.xia.quartz.service.JobEntityService;

public class NonQuartzJob {
	@Autowired
	JobEntityService jobEntityService;
	public void execute() {
		System.out.println("NonQuartzJob Runned:"+jobEntityService);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RuntimeException("test");
		//jobEntityService.findJobEntityByJobName("test");
	}
	public void setJobEntityService(JobEntityService jobEntityService) {
		this.jobEntityService = jobEntityService;
	}
}
