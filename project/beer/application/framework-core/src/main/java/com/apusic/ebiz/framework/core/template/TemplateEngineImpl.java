package com.apusic.ebiz.framework.core.template;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;


@Service("ebiz_TemplateEngine")
public class TemplateEngineImpl implements TemplateEngine, InitializingBean{
	private static final Log logger = LogFactory.getLog(TemplateEngineImpl.class);

	private Properties velocityProperties;

	private  VelocityEngine ve;


	public TemplateWrapper acceptTemplate(String templateLocation){
		Template template = null;
		try {
			template = ve.getTemplate(templateLocation);
		} catch (ResourceNotFoundException e) {
			logger.error("不能找到模板", e);
			throw new FrameworkRuntimeException("不能找到模板"+ templateLocation, e);
		} catch (ParseErrorException e) {
			logger.error("不能解析模板", e);
			throw new FrameworkRuntimeException("不能找到模板"+ templateLocation, e);
		} catch (Exception e) {
			logger.error(e);
			throw new FrameworkRuntimeException("生成模板时候出现异常： ", e);
		}
		return acceptTemplate(template);
	}

	public TemplateWrapper acceptTemplate(Template template){
		return new TemplateWrapper(template);
	}

	public void afterPropertiesSet() throws Exception {
		ve = new VelocityEngine();
		try {
			ve.init(velocityProperties);
		} catch (Exception e) {
			logger.error("不能初始化VelocityEngine", e);
			throw new FrameworkRuntimeException("不能初始化VelocityEngine ", e);
		}
	}

	public void setVelocityProperties(Properties velocityProperties) {
		this.velocityProperties = velocityProperties;
	}


}
