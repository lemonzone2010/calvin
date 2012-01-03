package com.xia.quartz.util;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.xia.quartz.model.JobEntity;

public class QuartzUtil {
	// TODO 变成service,同时变更状态
	private final static Log logger = LogFactory.getLog(QuartzUtil.class);
	private final static int DELAY_SECOND = 2;
	private final static String TRIGGER_PREFIX = "trigger_";
	private static Scheduler scheduler;
	static {
		try {
			logger.debug("开始初始化Scheduler");
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.startDelayed(DELAY_SECOND);
			logger.debug("初始化Scheduler成功");
		} catch (SchedulerException e) {
			logger.error("初始化Scheduler失败:" + e.getMessage(), e);
		}
	}

	public static void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException {
		JobDetail jobDetail = convertJob(jobEntity);
		CronTrigger triggerCorn = convertTrigger(jobEntity);
		Date ft = scheduler.scheduleJob(jobDetail, triggerCorn);
		logger.debug("任务:" + jobDetail.getKey() + " will run at: " + ft.toLocaleString());
	}

	private static CronTrigger convertTrigger(JobEntity jobEntity) {
		CronTrigger triggerCorn = TriggerBuilder.newTrigger()
				.withIdentity(TRIGGER_PREFIX + jobEntity.getJobName(), jobEntity.getJobGroupName())
				.withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getJobCronExpress())).build();
		return triggerCorn;
	}

	private static JobDetail convertJob(JobEntity jobEntity) throws ClassNotFoundException {
		logger.debug("Job converting:" + jobEntity);
		String jobClass = jobEntity.getJobClass();
		@SuppressWarnings("unchecked")
		Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobClass);
		JobDetail jobDetail = JobBuilder.newJob(clazz)
				.withIdentity(jobEntity.getJobName(), jobEntity.getJobGroupName())
				.withDescription(jobEntity.getJobDesc()).build();
		Set<Entry<String, String>> properties = jobEntity.getProperties().entrySet();
		for (Entry<String, String> entry : properties) {
			jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
		}
		return jobDetail;
	}

	public static void startScheduler() throws SchedulerException {
		scheduler.start();
		logger.debug("开始Scheduler:" + scheduler);
	}

	/**
	 * 暂停的JOB，后面启动时，会把暂停过程中的重新执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 */
	public static void pauseJob(JobEntity jobEntity) throws SchedulerException {
		JobKey jobKey = getJobkey(jobEntity);
		logger.debug("暂停JOB:" + jobKey);
		scheduler.pauseJob(jobKey);
		// scheduler.interrupt(jobKey);
	}

	private static TriggerKey getTriggerKey(JobEntity jobEntity) {
		return new TriggerKey(TRIGGER_PREFIX + jobEntity.getJobName(), jobEntity.getJobGroupName());
	}

	private static JobKey getJobkey(JobEntity jobEntity) {
		return new JobKey(jobEntity.getJobName(), jobEntity.getJobGroupName());
	}

	public static void resumeJob(JobEntity jobEntity) throws SchedulerException {
		JobKey jobKey = getJobkey(jobEntity);
		logger.debug("继续JOB:" + jobKey);
		scheduler.resumeJob(jobKey);
	}

	public static void unscheduleJob(JobEntity jobEntity) throws SchedulerException {
		JobKey jobKey = getJobkey(jobEntity);
		logger.debug("继续JOB:" + jobKey);
		scheduler.unscheduleJob(getTriggerKey(jobEntity));
	}

	public static void rescheduleJob(JobEntity jobEntity) throws SchedulerException {
		JobKey jobKey = getJobkey(jobEntity);
		logger.debug("继续JOB:" + jobKey);
		scheduler.rescheduleJob(getTriggerKey(jobEntity), convertTrigger(jobEntity));
	}

	public static void pauseAll() throws SchedulerException {
		scheduler.standby();
	}

	public static void shutdownAll() throws SchedulerException {
		scheduler.shutdown();
	}

}
