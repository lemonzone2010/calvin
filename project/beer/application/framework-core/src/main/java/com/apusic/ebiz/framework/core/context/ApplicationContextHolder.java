package com.apusic.ebiz.framework.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ctx = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

}
