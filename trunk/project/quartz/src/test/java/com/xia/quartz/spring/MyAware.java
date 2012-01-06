package com.xia.quartz.spring;

import com.xia.quartz.job.DemoSpringJob;

public interface MyAware {
	void setDemoSpringJob(DemoSpringJob demoSpringJob);
}
