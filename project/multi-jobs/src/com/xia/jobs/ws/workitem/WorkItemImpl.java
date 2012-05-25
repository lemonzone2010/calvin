package com.xia.jobs.ws.workitem;

import java.util.Date;

import org.apache.log4j.Logger;

public abstract class WorkItemImpl implements WsWorkItem {
	protected final Logger logger = Logger.getLogger(getClass());
	protected String url;
	protected String owerId;
	protected String workId;
	private Date createTime;
	private String creator;
	private String owerName;

	private String spIdentity;
	private String summary;
	private String priority;
	private String type;
	private Date expireTime;
	private Date doneTime;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOwerId() {
		return owerId;
	}

	public void setOwerId(String owerId) {
		this.owerId = owerId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getOwerName() {
		return owerName;
	}

	public void setOwerName(String owerName) {
		this.owerName = owerName;
	}

	public String getSpIdentity() {
		return spIdentity;
	}

	public void setSpIdentity(String spIdentity) {
		this.spIdentity = spIdentity;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

}
