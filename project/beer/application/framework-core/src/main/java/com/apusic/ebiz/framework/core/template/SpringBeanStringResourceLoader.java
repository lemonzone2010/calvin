package com.apusic.ebiz.framework.core.template;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;
import com.apusic.ebiz.framework.core.context.ApplicationContextHolder;

/**
 * Adapts the StringResourceLoader to allow the Velocity Engine to load the templates from
 * a specific Spring bean. Basically, the spring bean has a method returning a string.
 * 
 * @author xiaojunnuo
 *
 */
public class SpringBeanStringResourceLoader extends ResourceLoader {

	@Override
	public long getLastModified(Resource resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InputStream getResourceStream(String source)
			throws ResourceNotFoundException {

		TemplateResourceBean bean = (TemplateResourceBean) ApplicationContextHolder
				.getApplicationContext().getBean(source);
		Object object = ApplicationContextHolder.getApplicationContext()
				.getBean(bean.getBeanName());
		org.springframework.util.MethodInvoker methodInvoker = new org.springframework.util.MethodInvoker();
		String methodName = bean.getMethodName();
		methodInvoker.setTargetObject(object);
		methodInvoker.setTargetMethod(methodName);
		methodInvoker.setArguments(bean.getArgs());
		String value = null;
		try {
			methodInvoker.prepare();
			value = (String) methodInvoker.invoke();
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
			throw new FrameworkRuntimeException(
					"The method '"
							+ methodName
							+ "' is not accessible, please double check the configuration",
					e);
		}
		if (StringUtils.isNotBlank(value)){
			return IOUtils.toInputStream(value);
		}
		return null;
	}

	@Override
	public void init(ExtendedProperties configuration) {
		
		
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		return false;
	}
}
