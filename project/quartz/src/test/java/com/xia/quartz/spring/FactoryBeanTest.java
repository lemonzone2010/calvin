package com.xia.quartz.spring;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xia.quartz.job.DemoSpringJob;

public class FactoryBeanTest implements FactoryBean<MyBean>,BeanNameAware,MyAware {

	String name;
	DemoSpringJob demoSpringJob;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		FactoryBeanTest bean = new FactoryBeanTest();
		bean.getObject().hello();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-beantest.xml");
		MyBean bean2 = (MyBean) context.getBean("FactoryBeanTest");
		bean2.hello();
		
	}

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
		this.name=name;
		System.out.println("Name is:"+name);
	}

	public void setDemoSpringJob(DemoSpringJob demoSpringJob) {
		this.demoSpringJob = demoSpringJob;
		System.out.println("----");
	}

}

class MyBean {

	public void hello() {
		System.out.println("Hello");
	}
}