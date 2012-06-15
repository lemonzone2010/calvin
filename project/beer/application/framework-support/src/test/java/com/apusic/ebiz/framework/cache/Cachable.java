package com.apusic.ebiz.framework.cache;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public interface Cachable {
	
	@Cacheable(cacheName = "myCache")
	String getValue(String key);
	
	@Cacheable(cacheName = "myCache")
	String getValue(int key);
	
	
	
	void updateValue(int key);
	
	@TriggersRemove(cacheName = "myCache", removeAll=true, triggersRemoveInteceptorName="ebiz_TriggersRemoveInterceptor")
	void updateValue(String key);
	
	int getInvocationTimes();
}
