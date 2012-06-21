package com.apusic.ebiz.model.codegen.annotation;

import java.util.List;


public class EntityCodeGenBean {

	private String className;//类名
	private String fullClassName;//类全名
	private String chineseName;//类的中名描述，比如在页面可能是什么管理 
	private List<FieldViewBean> viewFields;//多个属性的定义
	private String subpackage;//子包名，比如这个属于order模块的，那这里就是order
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public List<FieldViewBean> getViewFields() {
		return viewFields;
	}

	public void setViewFields(List<FieldViewBean> viewFields) {
		this.viewFields = viewFields;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getSubpackage() {
		return subpackage;
	}

	public void setSubpackage(String subpackage) {
		this.subpackage = subpackage;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}
}
