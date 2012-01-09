package com.xia.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.xia.quartz.dao.Page;
import com.xia.quartz.model.JobEntity;

public interface QuartzFacade {
	//TODO add log query

	public void startJobs(List<JobEntity> jobEntitys) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	public void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException, NoSuchMethodException;

	public void startJobImmediatelyOnce(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException;

	public void startScheduler() throws SchedulerException;

	public void pauseJob(JobEntity jobEntity) throws SchedulerException;

	public void resumeJob(JobEntity jobEntity) throws SchedulerException;

	public void pauseAll() throws SchedulerException;

	public void shutdownAll() throws SchedulerException;

	public void saveJobEntity(JobEntity jobEntity);

	public void updateJobEntity(JobEntity jobEntity);

	public void removeJobEntity(JobEntity jobEntity);

	public void deleteById(Long id);

	public JobEntity getById(Long id);

	public JobEntity findJobEntityByJobName(String jobName);

	public List<JobEntity> getAllJobEntitys();

	public Page<JobEntity> getAllJobEntitysAsPage(Page<JobEntity> p);

	public boolean isExistJobEntity(String jobName);

}