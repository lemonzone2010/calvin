package com.apusic.ebiz.framework.core.workflow;


public class WorkflowException extends Exception{

	private Exception excepiton;
	private String rootCause;


	public WorkflowException(){

	}

	public WorkflowException(String rootCause){
		this.rootCause = rootCause;
	}

	public WorkflowException(Exception e){
		this.excepiton = e;
	}

	public Exception getRootcause(){
		return this.excepiton;
	}

	public String getRootCause(){
		return this.rootCause;
	}
}
