package com.apusic.ebiz.model.codegen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.apusic.ebiz.framework.core.template.TemplateEngine;
import com.apusic.ebiz.model.codegen.annotation.EntityCodeGenBean;
import com.apusic.ebiz.model.codegen.annotation.FieldViewAnnotationHelper;
import com.apusic.ebiz.model.util.Util;

public class CodeGeneratorImpl implements CodeGenerator {
	private static final Log logger = LogFactory.getLog(CodeGeneratorImpl.class);
	private TemplateEngine templateEngine;
	private FieldViewAnnotationHelper helper;

	public CodeGeneratorImpl() {
		templateEngine = new TemplateEngineImpl();
		helper = new FieldViewAnnotationHelper();
	}

	@Override
	public void process(Class<?> clazz, String... templateNames) {
		EntityCodeGenBean bean=helper.getBean(clazz);
		if(bean.getViewFields()==null ||bean.getViewFields().isEmpty()) {
			logger.warn("实体没有属性定义了@FieldView标记，不能自动生成代码.");
			return;
		}

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("bean", bean);
		context.put("stringUtils", StringUtils.class);
		context.put("util", Util.class);
		//CodeGeneratorImpl.class.getResource("/")+
		String subpackage="target/autoGenCode"+File.separatorChar+ bean.getSubpackage()+File.separatorChar+ clazz.getSimpleName();
		makeSubpackageDir(subpackage);

		for (String templateName : templateNames) {
			String outputName =subpackage +File.separatorChar+ StringUtils.substringBetween(templateName,"/", ".vm");
			outputName=outputName.replace("{0}", clazz.getSimpleName());
			logger.debug(String.format("正在生成代码：Class:%s,TemplateName:%s,OutputName:%s" , clazz.getSimpleName(), templateName,
					outputName));
			templateEngine.acceptTemplate(templateName).mergeIntoFile(context, createFile(outputName));
			logger.debug("successful generated!");
		}
	}

	private void makeSubpackageDir(String subpackage) {
		if(StringUtils.isNotEmpty(subpackage)) {
			File sub=new File(subpackage);
			if(!sub.exists()) {
				sub.mkdirs();
				logger.debug("路径生成完成:"+sub);
			}
		}
	}

	private File createFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			try {
				file.createNewFile();
				logger.debug("文件创建完成:"+file);
			} catch (IOException e) {
				logger.error("生成新文件出错", e);
			}
		}
		return file;
	}

}
