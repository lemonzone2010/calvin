package com.xia.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xia.quartz.dao.Page;
import com.xia.quartz.model.JobEntity;

@Service("quartzFacade")
public class QuartzFacadeImpl implements QuartzFacade {
	@Autowired
	private QuartzService quartzService;
	@Autowired
	private JobEntityService jobEntityService;

	// private JobLogEntityService jobLogEntityService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#startJobs(java.util.List)
	 */
	public void startJobs(List<JobEntity> jobEntitys) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException {
		quartzService.startJobs(jobEntitys);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#startJob(com.xia.quartz.model.JobEntity
	 * )
	 */
	public void startJob(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException, NoSuchMethodException {
		quartzService.startJob(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#startJobImmediatelyOnce(com.xia.
	 * quartz.model.JobEntity)
	 */
	public void startJobImmediatelyOnce(JobEntity jobEntity) throws SchedulerException, ClassNotFoundException,
			NoSuchMethodException {
		quartzService.startJobImmediatelyOnce(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#startScheduler()
	 */
	public void startScheduler() throws SchedulerException {
		quartzService.startScheduler();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#pauseJob(com.xia.quartz.model.JobEntity
	 * )
	 */
	public void pauseJob(JobEntity jobEntity) throws SchedulerException {
		quartzService.pauseJob(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#resumeJob(com.xia.quartz.model.JobEntity
	 * )
	 */
	public void resumeJob(JobEntity jobEntity) throws SchedulerException {
		quartzService.resumeJob(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#pauseAll()
	 */
	public void pauseAll() throws SchedulerException {
		quartzService.pauseAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#shutdownAll()
	 */
	public void shutdownAll() throws SchedulerException {
		quartzService.shutdownAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#saveJobEntity(com.xia.quartz.model
	 * .JobEntity)
	 */
	public void saveJobEntity(JobEntity jobEntity) {
		jobEntityService.saveJobEntity(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#updateJobEntity(com.xia.quartz.model
	 * .JobEntity)
	 */
	public void updateJobEntity(JobEntity jobEntity) {
		jobEntityService.updateJobEntity(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#removeJobEntity(com.xia.quartz.model
	 * .JobEntity)
	 */
	public void removeJobEntity(JobEntity jobEntity) {
		jobEntityService.removeJobEntity(jobEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#deleteById(java.lang.Long)
	 */
	public void deleteById(Long id) {
		jobEntityService.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#getById(java.lang.Long)
	 */
	public JobEntity getById(Long id) {
		return jobEntityService.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#findJobEntityByJobName(java.lang
	 * .String)
	 */
	public JobEntity findJobEntityByJobName(String jobName) {
		return jobEntityService.findJobEntityByJobName(jobName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xia.quartz.service.QuartzFacade1#getAllJobEntitys()
	 */
	public List<JobEntity> getAllJobEntitys() {
		return jobEntityService.getAllJobEntitys();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#getAllJobEntitysAsPage(com.xia.quartz
	 * .dao.Page)
	 */
	public Page<JobEntity> getAllJobEntitysAsPage(Page<JobEntity> p) {
		return jobEntityService.getAllJobEntitysAsPage(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xia.quartz.service.QuartzFacade1#isExistJobEntity(java.lang.String)
	 */
	public boolean isExistJobEntity(String jobName) {
		return jobEntityService.isExistJobEntity(jobName);
	}

	public boolean equals(Object obj) {
		return jobEntityService.equals(obj);
	}
}
