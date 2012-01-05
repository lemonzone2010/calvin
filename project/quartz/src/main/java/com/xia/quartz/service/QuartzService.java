package com.xia.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.xia.quartz.model.JobEntity;

public interface QuartzService {

	public void startJobs(List<JobEntity> jobEntitys) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	/**
	 * 它会等表达时时间才执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException, NoSuchMethodException;

	/**
	 * 仅仅执行一次，立即执行,仅对已存在的JOB而言
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public void startJobImmediatelyOnce(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	public void startScheduler() throws SchedulerException;

	/**
	 * 暂停的JOB，后面启动时，会把暂停过程中的重新执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 */
	public void pauseJob(JobEntity jobEntity) throws SchedulerException;

	public void resumeJob(JobEntity jobEntity) throws SchedulerException;

	public void unscheduleJob(JobEntity jobEntity) throws SchedulerException;

	public void rescheduleJob(JobEntity jobEntity) throws SchedulerException;

	public void pauseAll() throws SchedulerException;

	public void shutdownAll() throws SchedulerException;

}