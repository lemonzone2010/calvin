package com.xia.quartz.job;

import org.quartz.JobDataMap;

import com.xia.quartz.model.JobEntity;

public class DemoSpringJob  extends AbstractJob {

	@Override
	public void execute(JobDataMap jobDataMap) throws Exception {
		JobEntity job = getJobEntityService().findJobEntityByJobName("Demo任务_1325661555923");
		logger.debug("Finded:"+job);
	}

}
