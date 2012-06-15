package com.apusic.ebiz.framework.cache;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.event.EventPublisher;
import com.googlecode.ehcache.annotations.TriggersRemoveInterceptor;

/**
 * Triggers the clustered-node (global) to remove the cache
 * 
 * 
 * @author achen
 * 
 */
@Service("ebiz_TriggersRemoveInterceptor")
public class GlobalAwareTriggersRemoveInterceptor implements
		TriggersRemoveInterceptor {

	@Autowired
	private EventPublisher eventPublisher;

	public boolean preInvokeTriggersRemove(Ehcache cache,
			MethodInvocation methodInvocation, Serializable key) {
		RemoveCacheGlobalEvent removeCacheGlobalEvent = new RemoveCacheGlobalEvent(cache.getName(), key);
		eventPublisher.publish(removeCacheGlobalEvent);
		return true;
	}

	public boolean preInvokeTriggersRemoveAll(Ehcache cache,
			MethodInvocation methodInvocation) {
		RemoveCacheGlobalEvent removeCacheGlobalEvent = new RemoveCacheGlobalEvent(cache.getName(), null);
		eventPublisher.publish(removeCacheGlobalEvent);
		return true;
	}

}
