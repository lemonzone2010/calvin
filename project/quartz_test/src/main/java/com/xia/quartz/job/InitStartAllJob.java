package com.xia.quartz.job;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.xia.quartz.model.JobEntity;
import com.xia.quartz.service.JobEntityService;
import com.xia.quartz.service.QuartzService;
import com.xia.quartz.util.ApplicationContextHolder;

public class InitStartAllJob implements ServletContextListener {

	public void afterPropertiesSet() throws Exception {
		QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
		JobEntityService jobService = ApplicationContextHolder.getBean("jobEntityService");
		List<JobEntity> all = jobService.getAllJobEntitys();
		quartzService.startJobs(all);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			//afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
