package com.xia.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.xia.quartz.model.JobEntity;

/**
 * 对现在的任务进行管理
 * 原想针对spring有一个实现，针对quartz实现一个的，现仅实现对Spring的实现
 * @author calvinx
 *
 */
public interface QuartzService {
	/**
	 * 等待表达时时间才执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public void startJobs(List<JobEntity> jobEntitys) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	/**
	 * 等待表达时时间才执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException, NoSuchMethodException;

	/**
	 * 仅仅执行一次，立即执行,仅对已存在的JOB而言,注：它不会保存JOB，而是对Enitiy转化JOB且执行
	 * 
	 * @param jobEntity
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 */
	public void startJobImmediatelyOnce(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	/**
	 * 启动scheduler,默认自动已启动了，仅在shutDown后调用
	 * @throws SchedulerException
	 */
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