package com.apusic.ebiz.framework.core.fulltextsearch.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Filter {
	private String name;

	public Filter(String name){
		this.name = name;
	}

	private Map<String, Object> params = new HashMap<String, Object>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void put(String key, Object value){
		params.put(key, value);
	}

	public Object get(String key){
		return params.get(key);
	}

	public Set<String> getParameterKeys(){
		return params.keySet();
	}
}
