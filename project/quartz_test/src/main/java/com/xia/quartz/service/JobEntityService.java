package com.xia.quartz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xia.quartz.dao.CrudService;
import com.xia.quartz.dao.PageQueryService;
import com.xia.quartz.model.JobEntity;

@Component
@Service
public class JobEntityService {
	@Autowired
	private CrudService crudService;
	@Autowired
	PageQueryService pageQueryService;

	@Transactional
	public void saveJobEntity(JobEntity jobEntity) {
		crudService.create(jobEntity);
	}

	@Transactional
	public void updateJobEntity(JobEntity jobEntity) {
		crudService.update(jobEntity);
	}

	@Transactional
	public void removeJobEntity(JobEntity jobEntity) {
		crudService.delete(jobEntity);
	}

	@Transactional(readOnly = true)
	public JobEntity findJobEntityByJobName(String jobName) {
		List<JobEntity> list = pageQueryService.findListBy(JobEntity.class, "jobName", jobName);
		JobEntity ret = (list.isEmpty()) ? null : list.get(0);
		//if(null!=ret)ret.getProperties().isEmpty();// 把properties从Lazy中拿出来,如果有opensessioninview，可以去掉,如果开Lazy=false,会出现重复值
		return ret;
	}

	@Transactional(readOnly = true)
	public List<JobEntity> getAllJobEntitys() {
		List<JobEntity> findAll = pageQueryService.findAll(JobEntity.class);
		// 把properties从Lazy中拿出来
		/*for (JobEntity jobEntity : findAll) {
			jobEntity.getProperties().isEmpty();
		}*/
		return findAll;
	}

	public boolean isExistJobEntity(String jobName) {
		return null != findJobEntityByJobName(jobName);
	}
}
