package com.apusic.md.emarket.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.apusic.ebiz.datamanagement.service.ConfigurationDataService;
import com.apusic.ebiz.framework.core.template.TemplateEngine;
import com.apusic.ebiz.framework.core.template.TemplateWrapper;

public abstract class AbstractHtmlGenerator implements HtmlGenerator {


    protected static final String ENCODING = "UTF-8";

	private String templateLocation;

  
    @Autowired
	@Qualifier("datamanagement_ConfigurationDataService")
	protected ConfigurationDataService configurationDataService;

	@Autowired
	@Qualifier("ebiz_TemplateEngine")
	private TemplateEngine templateEngine;

	private String absoluteBasePath;

	protected TemplateWrapper getTemplateWrapper(){
		return (TemplateWrapper) templateEngine.acceptTemplate(templateLocation);
	}

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public String getAbsoluteBasePath() {
		return absoluteBasePath;
	}

	public void setAbsoluteBasePath(String absoluteBasePath) {
		this.absoluteBasePath = absoluteBasePath;
	}

	public void removeFile(String filePath) {
		if (!getTargetFolder().exists())
			return;
		File file = new File(getTargetFolder(), filePath);
		if (file.exists()) {
			boolean delete = file.delete();
		}
	}

	protected File getTargetFolder() {
		return new File(absoluteBasePath);
	}

    protected void write(String relativeFolder, String fileName, String htmlContent) throws IOException {
    	File folder = null;
    	if(relativeFolder==null){
    		folder = getTargetFolder();
    	}else{
    		folder = new File(getTargetFolder(), relativeFolder);
    	}
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IllegalStateException("can't create folder " + folder.getAbsolutePath());
            }
        }

        File file = new File(folder, fileName);
        OutputStreamWriter out = null ;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file), ENCODING);
            out.write(htmlContent);
        } finally {
        	 if (out != null) {
                 try {
                	 out.close();
                 } catch (IOException e) {
                 }
             }
        }
    }
    protected void fillConfiguraData(Map<String, Object> context) {
		context.put("loginUrl", configurationDataService.getValue("login.url"));
		context.put("logoutUrl", configurationDataService.getValue("logout.url"));
		context.put("server_ip", configurationDataService.getValue("server.ip"));
	}

}
