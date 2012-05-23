package com.xia.jobs;

public class ResponseStatus {
	public static final int OK = 0;
	public static final int FAILED = 1;
	private int status = OK;
	private String info;

	public ResponseStatus() {
	}

	public ResponseStatus(int status, String info) {
		this.status = status;
		this.info = info;
	}

	public ResponseStatus(Throwable failed) {
		status = FAILED;
		if (failed.getCause() != null) {
			info = failed + " caused by " + failed.getCause();
		} else {
			info = failed + "";
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setFail() {
		status = FAILED;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return status == OK;
	}

	@Override
	public String toString() {
		return "Result[status=" + status + ",info=" + info + "]";
	}
}

