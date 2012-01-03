package com.xia.quartz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xia.quartz.dao.CrudService;
import com.xia.quartz.dao.PageQueryService;
import com.xia.quartz.model.JobEntity;

@Component
@Service
public class JobService {
	@Autowired
	private CrudService crudService;
	@Autowired
	PageQueryService pageQueryService;

	public void addJob(JobEntity jobEntity) {
		crudService.create(jobEntity);
	}

	public void removeJob(JobEntity jobEntity) {
		crudService.delete(jobEntity);
	}

	public JobEntity findByJobName(String jobName) {
		List<JobEntity> list = pageQueryService.findListBy(JobEntity.class, "jobName", jobName);
		return (list.isEmpty()) ? null : list.get(0);
	}

	public boolean isExistJob(String jobName) {
		return null != findByJobName(jobName);
	}
}
