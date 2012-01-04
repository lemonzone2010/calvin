package com.xia.quartz.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.xia.quartz.service.JobEntityService;

public class NonQuartzJob {
	@Autowired
	JobEntityService jobEntityService;
	public void execute() {
		System.out.println("Ok");
		jobEntityService.findByJobName("test");
	}
}
