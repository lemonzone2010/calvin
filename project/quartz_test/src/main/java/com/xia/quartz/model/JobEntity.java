package com.xia.quartz.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
public class JobEntity extends IdEntity {
	@Column(unique = true)
	private String jobName;
	private String jobClass;
	private String jobCronExpress;
	private String jobDesc;
	private String jobGroupName;
	@ElementCollection
	@MapKeyColumn
	@CollectionTable(name = "t_job_properties")
	private Map<String, String> properties = new HashMap<String, String>();
	private int jobExecCount;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastExecTime;
	private boolean jobClassIsBeanName = false;// jobClass是一个Spring
												// Bean还是一个Class
	@Enumerated(EnumType.STRING)
	private JobStatus status = JobStatus.WAITTING;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobCronExpress() {
		return jobCronExpress;
	}

	public void setJobCronExpress(String jobCronExpress) {
		this.jobCronExpress = jobCronExpress;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public int getJobExecCount() {
		return jobExecCount;
	}

	public void setJobExecCount(int jobExecCount) {
		this.jobExecCount = jobExecCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastExecTime() {
		return lastExecTime;
	}

	public void setLastExecTime(Date lastExecTime) {
		this.lastExecTime = lastExecTime;
	}

	public boolean isJobClassIsBeanName() {
		return jobClassIsBeanName;
	}

	public void setJobClassIsBeanName(boolean jobClassIsBeanName) {
		this.jobClassIsBeanName = jobClassIsBeanName;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	/**
	 * 当配置不存在，或为null/""时，返回默认值。
	 * 
	 * @param name
	 * @param defaultValue
	 *            默认值。
	 * @return 当配置不存在，或为null/""时，返回默认值。
	 */
	public String getProperty(String name, String defaultValue) {
		String ret = (String) properties.get(name);
		return (ret == null || ret.length() == 0) ? defaultValue : ret;
	}

	public int getIntProperty(String property, int defaultValue) {
		String p = (String) getProperties().get(property);
		if (p == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(p);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public String getJobGroupName() {
		return jobGroupName;
	}

	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
}
