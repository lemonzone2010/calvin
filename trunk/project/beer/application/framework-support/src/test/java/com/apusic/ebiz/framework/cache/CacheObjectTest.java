package com.apusic.ebiz.framework.cache;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations={"classpath:apusic-ebiz-framework-core.xml", "classpath:apusic-ebiz-framework-event.xml",
		"classpath:apusic-ebiz-framework-integration-jms.xml", "classpath:apusic-ebiz-framework-event-test.xml",
		"classpath:apusic-ebiz-framework-cache.xml"})
public class CacheObjectTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private Cachable cachable;
	
	@Test
	public void getValue(){
		//invoke spring bean
		String value = cachable.getValue("abc");
		
		//Get from the cache
		value = cachable.getValue("abc");
		Assert.assertEquals(1, cachable.getInvocationTimes());
		
		//Remove from cache
		cachable.updateValue("abc");
		value = cachable.getValue("abc");
		Assert.assertEquals(2, cachable.getInvocationTimes());
	}
}