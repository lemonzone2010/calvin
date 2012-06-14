package com.apusic.ebiz.framework.core.workflow;

import java.util.Date;

public class Step {

	private String state;

	private Date operateTime;

	private String operation;

	private String operator;


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}


}
