package com.apusic.ebiz.framework.core.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;

public class TemplateWrapper extends Template{

	private static final Log logger = LogFactory.getLog(TemplateWrapper.class);

	protected static final String ENCODING = "UTF-8";

	private Template template;

	public TemplateWrapper(Template template){
		super();
		this.template = template;
	}


	public String mergeIntoString(Map context){
		StringWriter writer = new StringWriter();
		this.merge(context, writer);
		return writer.toString();
	}

	public void mergeIntoFile(Map context, File file){
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			this.merge(context, fileWriter);
			fileWriter.flush();
		} catch (IOException e) {
			logger.error("不能生存文件", e);
		}finally{
			IOUtils.closeQuietly(fileWriter);
		}
	}

	private void merge(Map context, Writer writer){
		VelocityContext velocityContext = new VelocityContext(context);
		try {
        	merge( (Context) velocityContext, writer );
		} catch (ResourceNotFoundException e) {
			logger.error("不能找到模板", e);
			throw new FrameworkRuntimeException("不能找到模板", e);
		} catch (ParseErrorException e) {
			logger.error("不能解析模板", e);
			throw new FrameworkRuntimeException("不能找到模板", e);
		} catch (IOException e) {
			logger.error(e);
			throw new FrameworkRuntimeException("生成模板时候出现异常： ", e);
		}
	}

	public void merge(Context context, Writer writer, List macroLibraries)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, IOException {
		template.merge(context, writer, macroLibraries);
	}

	public void initDocument() throws TemplateInitException{
		template.initDocument();
	}

	public boolean process() throws ResourceNotFoundException,
			ParseErrorException, IOException {
		return template.process();
	}
}
