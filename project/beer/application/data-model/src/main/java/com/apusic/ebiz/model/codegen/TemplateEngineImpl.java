package com.apusic.ebiz.model.codegen;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;
import com.apusic.ebiz.framework.core.template.TemplateEngine;
import com.apusic.ebiz.framework.core.template.TemplateWrapper;

public class TemplateEngineImpl implements TemplateEngine {
	private static final Log logger = LogFactory.getLog(TemplateEngineImpl.class);

	private VelocityEngine ve;

	public TemplateEngineImpl() {
		try {
			ve = new VelocityEngine();
			ve.init(getProperties());
		} catch (Exception e) {
			logger.error("不能初始化VelocityEngine", e);
			throw new FrameworkRuntimeException("不能初始化VelocityEngine ", e);
		}
	}

	/*-	public TemplateEngineImpl(Properties velocityProperties) {
	 try {
	 ve=new VelocityEngine();
	 ve.init(velocityProperties);
	 } catch (Exception e) {
	 logger.error("不能初始化VelocityEngine", e);
	 throw new FrameworkRuntimeException("不能初始化VelocityEngine ", e);
	 }
	 }*/

	public TemplateWrapper acceptTemplate(String templateFullName) {
		Template template = null;
		try {
			template = ve.getTemplate(templateFullName);
		} catch (ResourceNotFoundException e) {
			logger.error("不能找到模板", e);
			throw new FrameworkRuntimeException("不能找到模板" + templateFullName, e);
		} catch (ParseErrorException e) {
			logger.error("不能解析模板", e);
			throw new FrameworkRuntimeException("不能找到模板" + templateFullName, e);
		} catch (Exception e) {
			logger.error(e);
			throw new FrameworkRuntimeException("生成模板时候出现异常： ", e);
		}
		return acceptTemplate(template);
	}

	public TemplateWrapper acceptTemplate(Template template) {
		return new TemplateWrapper(template);
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		properties.put("resource.loader", "class");
		properties.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		properties.put("input.encoding", "UTF-8");
		properties.put("output.encoding", "UTF-8");
		// properties.put("velocimacro.library", "");
		return properties;
	}
}
