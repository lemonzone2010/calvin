package com.xia.quartz.spring;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xia.quartz.job.DemoSpringJob;
import com.xia.quartz.service.JobEntityService;

public class FactoryBeanTest implements FactoryBean<MyBean>, BeanNameAware, MyAware, InitializingBean,BeanFactoryAware {

	String name;
	DemoSpringJob demoSpringJob;
	BeanFactory beanFactory;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FactoryBeanTest bean = new FactoryBeanTest();
		bean.getObject().hello();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext-beantest.xml");
		MyBean bean2 = (MyBean) context.getBean("FactoryBeanTest");
		bean2.hello();

		DomainClass class1 = (DomainClass) context.getBean("domainClass");
		class1.test();
		DomainClass class2 = (DomainClass) context.getBean("domainClass");
		class2.test();
	}

	/*- Result:
	Name is:FactoryBeanTest
	postConstroct
	afterPropertiesSet
	MyBean.Hello
	 */

	@Override
	public MyBean getObject() throws Exception {
		return new MyBean();
	}

	@Override
	public Class<?> getObjectType() {
		return MyBean.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void setBeanName(String name) {
		this.name = name;
		System.out.println("Name is:" + name);
	}

	public void setDemoSpringJob(DemoSpringJob demoSpringJob) {
		this.demoSpringJob = demoSpringJob;
		System.out.println("----");
	}

	@PostConstruct
	public void postConstroct() {
		System.out.println("postConstroct");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet");
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}

