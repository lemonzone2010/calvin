package com.xia.quartz.servlet;

public final class Result {
	private boolean status;
	private String msg;
	private Object extend;// 作扩展用，暂时未�?

	public Object getExtend() {
		return extend;
	}

	public void setExtend(Object extend) {
		this.extend = extend;
	}

	public boolean isStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	private Result(boolean status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public static Result getSuccessResult() {
		return new Result(true, "");
	}

	public static Result getFailResult(String reason) {
		return new Result(false, reason);
	}

}