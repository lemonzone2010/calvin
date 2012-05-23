package com.xia.jobs.ws;

import java.util.ArrayList;
import java.util.List;

import com.xia.jobs.Response;
import com.xia.jobs.ResponseStatus;
import com.xia.jobs.WorkItem;

public class WorkResponse implements Response {
	private String requestUrl;
	protected String category;
	protected List<WorkItem> data = new ArrayList<WorkItem>(0);
	protected ResponseStatus status;
	private int size;
	private int queryTime;
	private String owerId;

	public String getRequestUrl() {
		return requestUrl;
	}

	public void validate() {
		// TODO Auto-generated method stub

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(int queryTime) {
		this.queryTime = queryTime;
	}

	public String getCategory() {
		return category;
	}

	@SuppressWarnings("unchecked")
	public List<WorkItem> getData() {
		return data;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public String getOwerId() {
		return owerId;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
