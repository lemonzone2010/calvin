package com.apusic.ebiz.framework.core.config;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:apusic-ebiz-framework-core.xml", "classpath:apusic-ebiz-framework-core-user.xml"})
public class ConfigServiceImplTest {

	@Autowired
	private ConfigService configService;

	@Test
	public void getValueByKey(){
		String value = configService.getValueByKey("resource.loader");
		assertEquals("class", value);
	}

	@Test
	public void getIntegerValueByKey(){
		int value = configService.getIntegerValueByKey("pageSize");
		assertEquals(20, value);
	}

}
