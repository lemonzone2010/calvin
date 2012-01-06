package com.xia.quartz.job;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.xia.quartz.service.JobEntityService;

public class DemoSpringJob  extends AbstractJob {
	@Autowired
	JobEntityService jobEntityService;
	@Override
	public void execute(JobDataMap jobDataMap) throws Exception {
		//JobEntity job = getJobEntityService().findJobEntityByJobName("Demo任务_1325661555923");
		logger.debug("DemoSpringJob Runned:");
	}

}
