package com.xia.quartz.job;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class InvokerJobBeanTest {
	@Autowired
	InvokerJobBean invokerJobBean;

	@Test
	public void testInvokerJobBean() throws SchedulerException, ClassNotFoundException, InterruptedException,
			NoSuchMethodException {
		// InvokerJobBean s =
		// ApplicationContextHolder.getBean("invokerJobBean");
		invokerJobBean.setTargetBeanName("nonQuartzJob");
		invokerJobBean.setTargetMethod("execute");
		invokerJobBean.afterPropertiesSet();
		JobDetail object = invokerJobBean.getObject();
		Assert.assertNotNull(object);
	}
}
