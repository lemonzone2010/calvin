package com.apusic.ebiz.framework.core.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
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
			if(applicationContext.containsBean(PropertySourcesPlaceholderConfigurer.class.getName())) {
				//定义为使用<context:placeholder的方式
				PropertySourcesPlaceholderConfigurer propConfigurer = applicationContext.getBean(PropertySourcesPlaceholderConfigurer.class);
				loadProperties(propConfigurer);
			}
			else {
				// <bean				class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
				Map<String, PropertyPlaceholderConfigurer> configers = applicationContext.getBeansOfType(PropertyPlaceholderConfigurer.class);
				for (Entry<String, PropertyPlaceholderConfigurer> config : configers.entrySet()) {
					loadProperties(config.getValue());
				}
			}
		} catch (Exception e) {
			throw new FrameworkRuntimeException("Failed to load properties configuration file", e);
		}
	}

	private void loadProperties(PlaceholderConfigurerSupport propConfigurer) throws IllegalAccessException, IOException, SecurityException, NoSuchFieldException {
		Field field = propConfigurer.getClass().getSuperclass().getSuperclass().getSuperclass()
				.getDeclaredField("locations");
		field.setAccessible(true);
		Resource[] locations = (Resource[]) field.get(propConfigurer);
		this.setLocations(locations);
		this.loadProperties(properties);
	}

	public int getIntegerValueByKey(String key) {
		String value = this.getValueByKey(key);
		return NumberUtils.toInt(value);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
