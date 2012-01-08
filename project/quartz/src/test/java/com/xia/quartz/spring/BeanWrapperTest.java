package com.xia.quartz.spring;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Object obj = Class.forName("com.xia.quartz.spring.BeanWrapperTest").newInstance();
		BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
		beanWrapper.setPropertyValue("name", "value");
		Object propertyValue = beanWrapper.getPropertyValue("name");
		System.out.println(propertyValue);

	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
