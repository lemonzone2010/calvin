package com.xia.quartz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xia.quartz.dao.CrudService;
import com.xia.quartz.dao.PageQueryService;
import com.xia.quartz.model.JobLogEntity;

@Component
@Service
public class JobLogEntityService {
	@Autowired
	private CrudService crudService;
	@Autowired
	PageQueryService pageQueryService;

	@Transactional
	public void addJobLog(JobLogEntity jobLogEntity) {
		crudService.create(jobLogEntity);
	}
}
