package com.xia.quartz.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class NopJob implements Job{
	private final static Log logger = LogFactory.getLog(NopJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("开始执行NopJob:"+context.getJobDetail().getKey());
	}

}
