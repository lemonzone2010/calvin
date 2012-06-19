package com.apusic.ebiz.framework.core.config;

import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

@Service("ConfigServiceImpl")
public class ConfigServiceImpl extends PropertiesLoaderSupport implements ConfigService, InitializingBean,
		ApplicationContextAware {
	private ApplicationContext applicationContext;

	private Properties properties = new Properties();

	public String getValueByKey(String key) {
		return properties.getProperty(key);
	}

	public void afterPropertiesSet() {
		try {
			PropertySourcesPlaceholderConfigurer propConfigurer = applicationContext
					.getBean(PropertySourcesPlaceholderConfigurer.class);
			Field field = propConfigurer.getClass().getSuperclass().getSuperclass().getSuperclass()
					.getDeclaredField("locations");
			field.setAccessible(true);
			Resource[] locations = (Resource[]) field.get(propConfigurer);
			this.setLocations(locations);
			this.loadProperties(properties);
		} catch (Exception e) {
			throw new FrameworkRuntimeException("Failed to load properties configuration file", e);
		}
	}

	public int getIntegerValueByKey(String key) {
		String value = this.getValueByKey(key);
		return NumberUtils.toInt(value);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
