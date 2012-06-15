package com.apusic.ebiz.framework.event;

import java.io.Serializable;

/**
 * Adapters the local event to global event and then publish to the global event context.
 * The global event context is a clustered Message Queue
 * 
 * @author achen
 */
public class GlobalEvent<T extends Serializable> extends Event{
	private String eventTopic;

	public GlobalEvent(T source) {
		super(source);
		this.eventTopic = "eventTopic";
	}

	public GlobalEvent(T source, String eventTopic){
		super(source);
		this.eventTopic = eventTopic;
	}

	public String getEventTopic() {
		return eventTopic;
	}
	
	public Object getSource() {
        return this;
    }
	
}