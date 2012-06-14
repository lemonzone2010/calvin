package com.apusic.ebiz.framework.core.validation;


public class ValidationReport {
	public enum Severity {ERROR, WARNING, INFO}

	private String code;
	private Object[] args;
	private Severity serverity;

	public ValidationReport(String code,  Severity level, Object... args){
		this.code = code;
		this.serverity = level;
		this.args = args;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Severity getSeverity() {
		return serverity;
	}

	public void setSeverity(Severity serverity) {
		this.serverity = serverity;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Severity getServerity() {
		return serverity;
	}

	public void setServerity(Severity serverity) {
		this.serverity = serverity;
	}



}
