package com.xia.quartz.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.model.JobLogEntity;
import com.xia.quartz.model.JobLogStatus;
import com.xia.quartz.model.JobStatus;
import com.xia.quartz.service.JobEntityService;
import com.xia.quartz.service.JobLogEntityService;
import com.xia.quartz.service.TrascationHelper;
import com.xia.quartz.util.ApplicationContextHolder;
import com.xia.quartz.util.Util;

/**
 * 这里的服务类只能使用此方法取得: ApplicationContextHolder.getBean("jobLogEntityService");
 * 
 * @author xiayong
 * 
 */
public abstract class AbstractJob extends QuartzJobBean {
	protected final static Log logger = LogFactory.getLog(AbstractJob.class);

	@Override
	protected final void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();

		JobEntity jobEntity = getJobEntityService().findJobEntityByJobName(jobDetail.getKey().getName());
		JobLogEntity logEntity = preExecute(context, jobEntity);

		try {
			long start = System.currentTimeMillis();

			execute(jobDetail.getJobDataMap());//

			jobEntity.setJobUsedTime(System.currentTimeMillis() - start);
			jobEntity.setStatus(JobStatus.WAITTING);
			getJobEntityService().updateJobEntity(jobEntity);
			getJobLogEntityService().addJobLog(logEntity);
		} catch (Exception e) {
			logger.error("任务执行出错" + e.getMessage(), e);
			dealException(jobEntity, logEntity, e);
			throw new JobExecutionException(e);
		}

	}

	private JobLogEntity preExecute(JobExecutionContext context, JobEntity jobEntity) throws JobExecutionException {
		if (jobEntity == null) {
			logger.error("您要执行的任务不存在:" + context.getJobDetail().getName());
			throw new JobExecutionException("任务不存在:" + context.getJobDetail().getName());
		}

		jobEntity.setStatus(JobStatus.RUNNING);
		jobEntity.setLastExecTime(new Date());
		jobEntity.setNextExecTime(context.getNextFireTime());
		jobEntity.setJobExecCount(jobEntity.getJobExecCount() + 1);

		JobLogEntity logEntity = new JobLogEntity();
		logEntity.setJobEntity(jobEntity);
		getJobEntityService().updateJobEntity(jobEntity);
		return logEntity;
	}

	private void dealException(JobEntity jobEntity, JobLogEntity logEntity, Exception e) throws JobExecutionException {
		ExceptionEventDispather.getInstance().notify(jobEntity);
		logEntity.setStatus(JobLogStatus.FAIL);
		logEntity.setExceptionStackTrace(Util.getStackTrack(e));
		try {
			getJobLogEntityService().addJobLog(logEntity);
			jobEntity.setJobExceptionCount(jobEntity.getJobExceptionCount() + 1);
			jobEntity.setStatus(JobStatus.EXCEPTION);
			jobEntity.setLastExeceptionTime(new Date());
			getJobEntityService().updateJobEntity(jobEntity);
		} catch (Exception e1) {
			throw new JobExecutionException(e1);
		}
	}

	@Transactional
	public abstract void execute(JobDataMap jobDataMap) throws Exception;

	private JobLogEntityService getJobLogEntityService() {
		return ApplicationContextHolder.getBean("jobLogEntityService");
	}

	private JobEntityService getJobEntityService() {
		return ApplicationContextHolder.getBean("jobEntityService");
	}

	@SuppressWarnings("unused")
	private TrascationHelper getTrascationHelper() {
		return ApplicationContextHolder.getBean("trascationHelper");
	}

}
