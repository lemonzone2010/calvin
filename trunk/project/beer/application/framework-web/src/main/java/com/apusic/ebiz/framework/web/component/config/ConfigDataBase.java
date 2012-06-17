/*package com.apusic.ebiz.framework.web.component.config;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.operamasks.faces.component.GenComponentUtils;
import org.operamasks.faces.tools.annotation.ComponentMeta;

@ComponentMeta(tagName = "configData", family = "com.apusic.ebiz.framework.web.component.config.ConfigDataBase", rendererType = "com.apusic.ebiz.framework.web.component.config.ConfigDataBase")
public class ConfigDataBase extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "com.apusic.ebiz.framework.web.component.config.ConfigDataBase";
	public static final String COMPONENT_TYPE = "com.apusic.ebiz.framework.web.component.config.ConfigDataBase";

	private String key;

	private Boolean el;

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[])state;
        super.restoreState(context, values[0]);
        this.el = (Boolean) values[1];
        this.key = (String) values[2];
	}

	@Override
	public Object saveState(FacesContext context) {
		return new Object[]{
				super.saveState(context),
				this.el,
				this.key
		};
	}

	public Boolean isEl() {
		if (this.el != null) {
			return this.el;
		}
		Object exprValue = GenComponentUtils.getValueFromExpression(this,
				"el");
		if (exprValue != null) {
			return (java.lang.Boolean) exprValue;
		}
		return false;
	}

	public void setEl(boolean el) {
		this.el = el;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
*/