package com.apusic.ebiz.framework.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service("ebiz_RemoveCacheEventListener")
public class RemoveCacheGlobalEventListener implements ApplicationListener {

	private final static Log log = LogFactory.getLog(RemoveCacheGlobalEventListener.class);
	
	@Autowired
	@Qualifier("ebiz_ehCacheManager")
	private CacheManager cacheManager;
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof RemoveCacheGlobalEvent){
			RemoveCacheGlobalEvent removeCacheEvent = (RemoveCacheGlobalEvent) event;
			Cache cache = cacheManager.getCache(removeCacheEvent.getCacheName());
			if (cache == null) {
				return;
			}  
			if (removeCacheEvent.getCacheKey() == null){
				cache.removeAll();  
			}else if (cache.isKeyInCache(removeCacheEvent.getCacheKey())){
					cache.remove(removeCacheEvent.getCacheKey());
            }
			
		}
	}
}
