package com.xia.quartz.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitStartAllJobListenerTest {
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		new InitStartAllJobListener().afterPropertiesSet();
		// startAll();
		// new JobTest().testStartInvokerJob();//
		// 测试时，为了JOB连续性，只能使用MAIN启动，不用@Test
	}

	/*-	private static void startAll() throws SchedulerException, ClassNotFoundException, NoSuchMethodException {
	 QuartzService quartzService = ApplicationContextHolder.getBean("quartzService");
	 JobEntityService jobService = ApplicationContextHolder.getBean("jobEntityService");
	 List<JobEntity> all = jobService.getAllJobEntitys();
	 quartzService.startJobs(all);
	 }*/

}
