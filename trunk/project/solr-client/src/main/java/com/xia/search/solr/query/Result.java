package com.xia.search.solr.query;


public final class Result {
	private boolean status;
	private String msg;
	private Object extend;// 作扩展用，暂时未用
	private long usedTime;

	public static Result getResult(boolean status, long usedTime) {
		Result ret=new Result(status, "");
		ret.usedTime = usedTime;
		return ret;
	}

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

	public long getUsedTime() {
		return usedTime;
	}

}