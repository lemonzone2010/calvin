package com.xia.quartz.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xia.quartz.job.InvokerJobBean;
import com.xia.quartz.model.JobEntity;
import com.xia.quartz.model.JobStatus;
import com.xia.quartz.util.ApplicationContextHolder;

@Component("quartzService")
@Service
public class QuartzServiceSpringImpl implements QuartzService, InitializingBean {
	private final static Log logger = LogFactory.getLog(QuartzServiceSpringImpl.class);
	private final static String TRIGGER_PREFIX = "trigger_";
	@Autowired
	@Qualifier("scheduler")
	private SchedulerFactoryBean schedulerFactoryBean;
	private static Scheduler scheduler;
	@Autowired
	private JobEntityService jobEntityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzService#startJob(com.xia.quartz.model.JobEntity
	 * )
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException, NoSuchMethodException {
		try {
			JobDetail jobDetail = convertJob(jobEntity);
			CronTrigger triggerCorn = convertTrigger(jobEntity);
			if (StringUtils.isNotBlank(jobEntity.getJobGroupName())) {
				jobEntity.setJobGroupName(jobDetail.getGroup());
			}
			jobEntity.setStatus(JobStatus.RUNNING);
			jobEntityService.updateJob(jobEntity);

			Date ft = scheduler.scheduleJob(jobDetail, triggerCorn);
			logger.debug("任务:" + jobDetail.getKey() + " will run at: " + ft.toLocaleString());
		} catch (ParseException e) {
			logger.error("任务转换失败:" + e.getMessage(), e);
			throw new SchedulerException(e);
		}
	}

	private CronTrigger convertTrigger(JobEntity jobEntity) throws ParseException {
		CronTrigger triggerCorn = new CronTrigger(TRIGGER_PREFIX + jobEntity.getJobName(), jobEntity.getJobGroupName(),
				jobEntity.getJobCronExpress());
		return triggerCorn;
	}

	private JobDetail convertJob(JobEntity jobEntity) throws ClassNotFoundException, NoSuchMethodException {
		logger.debug("Job生成中:" + jobEntity.getJobName());

		JobDetail jobDetail;
		if (jobEntity.isJobClassIsBeanName()) {
			//jobDetail = ApplicationContextHolder.getBean(jobEntity.getJobClass());
			InvokerJobBean bean=ApplicationContextHolder.getBean("invokerJobBean");
			bean.setTargetBeanName(jobEntity.getJobClass());
			bean.setTargetMethod(jobEntity.getJobMethod());
			bean.afterPropertiesSet();
			jobDetail=bean.getObject();
		} else {
			String jobClass = jobEntity.getJobClass();
			@SuppressWarnings("unchecked")
			Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobClass);
			jobDetail = ApplicationContextHolder.getBean("jobDetail");
			jobDetail.setJobClass(clazz);
		}
		jobDetail.setName(jobEntity.getJobName());
		jobDetail.setGroup(jobEntity.getJobGroupName());
		jobDetail.setDescription(jobEntity.getJobDesc());
		jobDetail.getJobDataMap().putAll(jobEntity.getProperties());
		return (JobDetail) jobDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzService#startScheduler()
	 */
	@Override
	public void startScheduler() throws SchedulerException {
		scheduler.start();
		logger.debug("开始Scheduler:" + scheduler);
		// 状态未处理
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzService#pauseJob(com.xia.quartz.model.JobEntity
	 * )
	 */
	@Override
	public void pauseJob(JobEntity jobEntity) throws SchedulerException {
		logger.debug("暂停JOB:" + jobEntity);
		scheduler.pauseJob(jobEntity.getJobName(), jobEntity.getJobGroupName());
		// scheduler.interrupt(jobKey);
		jobEntity.setStatus(JobStatus.PAUSED);
		jobEntityService.updateJob(jobEntity);
	}

	@Override
	public void resumeJob(JobEntity jobEntity) throws SchedulerException {
		logger.debug("继续JOB:" + jobEntity.getJobName() + jobEntity.getJobGroupName());
		scheduler.resumeJob(jobEntity.getJobName(), jobEntity.getJobGroupName());
		jobEntity.setStatus(JobStatus.RUNNING);
		jobEntityService.updateJob(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzService#unscheduleJob(com.xia.quartz.model
	 * .JobEntity)
	 */
	@Override
	public void unscheduleJob(JobEntity jobEntity) throws SchedulerException {
		logger.debug("卸载JOB:" + jobEntity.getJobName() + jobEntity.getJobGroupName());
		scheduler.unscheduleJob(jobEntity.getJobName(), jobEntity.getJobGroupName());
		jobEntity.setStatus(JobStatus.WAITTING);
		jobEntityService.updateJob(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzService#rescheduleJob(com.xia.quartz.model
	 * .JobEntity)
	 */
	@Override
	public void rescheduleJob(JobEntity jobEntity) throws SchedulerException {
		logger.warn("未实现");
		// logger.debug("重新启动JOB:" + jobKey);
		// scheduler.rescheduleJob(getTriggerKey(jobEntity),
		// convertTrigger(jobEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzService#pauseAll()
	 */
	@Override
	public void pauseAll() throws SchedulerException {
		logger.warn("暂停所有");
		scheduler.standby();
		// 状态未处理
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzService#shutdownAll()
	 */
	@Override
	public void shutdownAll() throws SchedulerException {
		logger.warn("服务关闭");
		// scheduler.getJobNames(null);
		scheduler.shutdown();
	}

	@Override
	public void startJobs(List<JobEntity> jobEntitys) throws SchedulerException, ClassNotFoundException, NoSuchMethodException {
		for (JobEntity jobEntity : jobEntitys) {
			startJob(jobEntity);
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduler = schedulerFactoryBean.getScheduler();
	}

}
