package com.apusic.ebiz.framework.integration.jms;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

class MethodInvoker {

	static void invokeMethod(ApplicationContext appContext,
			String invokeMethod, Object[] args) {
		String[] beanAndMethod = StringUtils.split(invokeMethod, ".");
		String beanName = beanAndMethod[0];
		String methodName = beanAndMethod[1];
		Object bean = appContext.getBean(beanName);

		org.springframework.util.MethodInvoker methodInvoker = new org.springframework.util.MethodInvoker();
		methodInvoker.setTargetObject(bean);
		methodInvoker.setTargetMethod(methodName);
		methodInvoker.setArguments(args);
		try {
			methodInvoker.prepare();
			methodInvoker.invoke();
		} catch (ClassNotFoundException e) {
			throw new FrameworkRuntimeException(
					"The method '"
							+ methodName
							+ "' invoked is not accesible, please double check the configuration",
					e);
		} catch (NoSuchMethodException e) {
			throw new FrameworkRuntimeException("There is no such method '"
					+ methodName + "', please double check the configuration",
					e);
		} catch (InvocationTargetException e) {
			throw new FrameworkRuntimeException("There is no such method '"
					+ methodName + "', please double check the configuration",
					e);
		} catch (IllegalAccessException e) {
			throw new FrameworkRuntimeException("The method '"
					+ methodName + "' is not accessible, please double check the configuration",
					e);
		}

	}
}
