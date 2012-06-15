package com.apusic.ebiz.framework.event;

import java.io.Serializable;

public class LocalEvent<T extends Serializable> extends Event{

	private T globalSource;
	
	public LocalEvent(T source) {
		super(source);
		globalSource = source;
	}

	public T getGlobalSource() {
		return globalSource;
	}
	
	public void setSource(T t){
		this.source = t;
	}
}
