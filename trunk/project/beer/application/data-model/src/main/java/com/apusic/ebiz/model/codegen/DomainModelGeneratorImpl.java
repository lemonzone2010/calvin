package com.apusic.ebiz.model.codegen;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.apusic.ebiz.framework.core.template.TemplateEngine;
import com.apusic.ebiz.model.util.Util;

public class DomainModelGeneratorImpl implements CodeGenerator {
	private static final Log logger = LogFactory.getLog(CodeGeneratorImpl.class);
	private TemplateEngine templateEngine;

	public DomainModelGeneratorImpl() {
		templateEngine = new TemplateEngineImpl();
	}

	@Override
	public void process(Class<?> clazz, String... templateNames) {
		Field[] fields = clazz.getDeclaredFields();
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("fields", fields);
		context.put("stringUtils", StringUtils.class);
		context.put("util", Util.class);
		context.put("clazz", clazz);

		logger.debug("代码生成中:");
		for (String templateName : templateNames) {
			System.out.println("\n\n");
			System.out.println("\n" + templateEngine.acceptTemplate(templateName).mergeIntoString(context));
			System.out.println("\n\n");
		}
		logger.debug("successful generated!");
	}
}
