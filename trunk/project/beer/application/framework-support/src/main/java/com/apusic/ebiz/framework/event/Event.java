package com.apusic.ebiz.framework.event;

import org.springframework.context.ApplicationEvent;

public class Event extends ApplicationEvent{

	private String domain;
	private String creator;
	private int type;

	public Event(Object source) {
		super(source);
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
