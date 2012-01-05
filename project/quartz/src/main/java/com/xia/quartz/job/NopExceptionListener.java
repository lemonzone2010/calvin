package com.xia.quartz.job;

import com.xia.quartz.model.JobEntity;

public class NopExceptionListener extends JobExceptionListener{

	@Override
	public void update(JobEntity jobEntity) {
		logger.debug("excepted in :"+jobEntity);
		
	}

}
