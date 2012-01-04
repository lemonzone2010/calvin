package com.xia.quartz.service;

import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component("jobDetail")
public class JobDetailImpl extends JobDetailBean {
	public JobDetailImpl() {
		super();
	}

}
