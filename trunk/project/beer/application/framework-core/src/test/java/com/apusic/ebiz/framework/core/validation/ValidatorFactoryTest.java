package com.apusic.ebiz.framework.core.validation;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
public class ValidatorFactoryTest {

	@Autowired
	private ValidatorFactory validatorFactory;

	@Test
	public void getValidator(){
		Validator validator = validatorFactory.getValidator("mockUserNameValidator");
		assertNotNull(validator);
	}
}
