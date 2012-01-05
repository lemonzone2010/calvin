package com.xia.quartz.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	/**
	 * 在设置bean的名称时，要以接口名，且第一个字母小写，如DemoService,在@Component("demOservice")
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		T bean = (T) ctx.getBean(StringUtils.uncapitalize(clazz.getSimpleName()));
		return (T) bean;
	}
	public static <T> T getBean(String name) {
		return (T) ctx.getBean(name);
	}

}
