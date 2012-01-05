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
import javax.persistence.FetchType;
import javax.persistence.MapKeyClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
public class JobEntity extends IdEntity {
	@NotBlank
	@Column(unique = true)
	private String jobName;//任务名
	@NotBlank
	private String jobClass;//类名或者bean名
	private String jobMethod;//如果为bean名，对应执行的方法
	@NotNull
	private String jobCronExpress;//表达式
	private String jobDesc;//任务描述
	private String jobGroupName;//Group名
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "t_job_properties")
	private Map<String, String> properties = new HashMap<String, String>();
	private int jobExecCount;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime = new Date();
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastExecTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextExecTime;
	// true=继承Job类，false=spring bean,没有继承job类
	private boolean jobClassIsBeanName = false;
	@Enumerated(EnumType.STRING)
	private JobStatus status = JobStatus.WAITTING;
	
	private long jobUsedTime;//ms

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
	@JsonIgnore
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

	public Date getNextExecTime() {
		return nextExecTime;
	}

	public void setNextExecTime(Date nextExecTime) {
		this.nextExecTime = nextExecTime;
	}

	@Override
	public String toString() {
		return "JobEntity [jobName=" + jobName + ", jobClass=" + jobClass + ", jobCronExpress=" + jobCronExpress
				+ ", jobDesc=" + jobDesc + ", jobGroupName=" + jobGroupName + ", properties=" + properties
				+ ", jobExecCount=" + jobExecCount + ", createTime=" + createTime + ", lastExecTime=" + lastExecTime
				+ ", nextExecTime=" + nextExecTime + ", jobClassIsBeanName=" + jobClassIsBeanName + ", status="
				+ status + "]";
	}

	public String getJobMethod() {
		return jobMethod;
	}

	public void setJobMethod(String jobMethod) {
		this.jobMethod = jobMethod;
	}

	public long getJobUsedTime() {
		return jobUsedTime;
	}

	public void setJobUsedTime(long jobUsedTime) {
		this.jobUsedTime = jobUsedTime;
	}

}
