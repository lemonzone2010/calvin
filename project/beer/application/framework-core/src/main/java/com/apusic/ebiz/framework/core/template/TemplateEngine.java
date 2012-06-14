package com.apusic.ebiz.framework.core.template;

import org.apache.velocity.Template;

public interface TemplateEngine {

	public TemplateWrapper acceptTemplate(String templateLocation);

	public TemplateWrapper acceptTemplate(Template template);

}
