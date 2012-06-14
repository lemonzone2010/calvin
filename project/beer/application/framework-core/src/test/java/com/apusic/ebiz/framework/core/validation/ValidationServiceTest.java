package com.apusic.ebiz.framework.core.validation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.apusic.ebiz.framework.core.DummyUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-user.xml" })
public class ValidationServiceTest {

	@Autowired
	@Qualifier("ebiz_userValidationService")
	private ValidationService validationService;

	private DummyUser dummyUser;

	@Before
	public void setup(){
		dummyUser = new DummyUser();
	}

	@Test
	public void validate(){
		dummyUser.setName("name");
		ValidationReports reports = validationService.validate(dummyUser);
		assertNotNull(reports);

		assertTrue(reports.getValidationReports().size() == 2);


	}
}
