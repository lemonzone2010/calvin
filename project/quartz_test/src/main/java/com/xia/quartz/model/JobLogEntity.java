package com.xia.quartz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

//TODO 增加操作人
@SuppressWarnings("serial")
@Entity
public class JobLogEntity extends IdEntity {
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date execTime = new Date();
	@Enumerated(EnumType.STRING)
	private JobLogStatus status = JobLogStatus.SUCCESS;

	@ManyToOne
	private JobEntity jobEntity = new JobEntity();
	@Column(length = 4000)
	private String exceptionStackTrace;

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public JobLogStatus getStatus() {
		return status;
	}

	public void setStatus(JobLogStatus status) {
		this.status = status;
	}

	public JobEntity getJobEntity() {
		return jobEntity;
	}

	public void setJobEntity(JobEntity jobEntity) {
		this.jobEntity = jobEntity;
	}

	public String getExceptionStackTrace() {
		return exceptionStackTrace;
	}

	public void setExceptionStackTrace(String exceptionStackTrace) {
		if (StringUtils.length(exceptionStackTrace) > 4000) {
			exceptionStackTrace = StringUtils.substring(exceptionStackTrace, 0, 4000);
		}
		this.exceptionStackTrace = exceptionStackTrace;
	}

	@Override
	public String toString() {
		return "JobLogEntity [execTime=" + execTime + ", status=" + status + ", jobEntity=" + jobEntity
				+ ", exceptionStackTrace=" + exceptionStackTrace + "]";
	}
	
}
