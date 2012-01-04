package com.xia.quartz.job;

import org.quartz.JobDataMap;

public class DemoJob extends AbstractJob {

	@Override
	public void execute(JobDataMap jobDataMap) throws Exception {
		logger.debug("DEMO JOB开始运行:"+jobDataMap.getWrappedMap());

	}

}
