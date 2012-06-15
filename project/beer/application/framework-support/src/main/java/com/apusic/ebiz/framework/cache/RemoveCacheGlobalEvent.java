package com.apusic.ebiz.framework.cache;

import java.io.Serializable;

import com.apusic.ebiz.framework.event.GlobalEvent;

public class RemoveCacheGlobalEvent extends GlobalEvent{
	private String cacheName;
	private Serializable cacheKey;
	
	public RemoveCacheGlobalEvent( String cacheName, Serializable  cacheKey) {
		super(cacheName);
		this.cacheName = cacheName;
		this.cacheKey = cacheKey;
	}
	
	public RemoveCacheGlobalEvent(Serializable source) {
		super(source);
		
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Serializable getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(Serializable cacheKey) {
		this.cacheKey = cacheKey;
	}
	
}