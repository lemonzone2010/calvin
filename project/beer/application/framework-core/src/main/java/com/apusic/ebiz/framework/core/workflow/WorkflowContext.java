package com.apusic.ebiz.framework.core.workflow;

import java.util.HashMap;
import java.util.Map;

public class WorkflowContext {

	private Map<String, Object> context = new HashMap<String, Object>();

	public void set(String key, Object value){
		context.put(key, value);
	}

	public Object get(String key){
		return context.get(key);
	}

	public Map<String, Object> getInnerContext(){
		return context;
	}
}
