package com.apusic.ebiz.framework.core.validation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("ebiz_ValidatorFactory")
public class ValidatorFactoryImpl implements ValidatorFactory, ApplicationContextAware{

	private ApplicationContext applicationContext;

	public Validator getValidator(String name) {
		return (Validator) applicationContext.getBean(name);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
