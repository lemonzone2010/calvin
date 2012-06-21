package com.apusic.ebiz.model.codegen.annotation;

public enum FieldViewType {
	STRING("默认的字符串"), 
	DATE("日期"), 
	DATETIME("日期时间"), 
	INT("整形"), 
	COMBOX("下拉列表"), 
	DOUBLE("浮点"), 
	;
	private String label;

	public String getLabel() {
		return label;
	}

	private FieldViewType(String label) {
		this.label = label;
	}
}
