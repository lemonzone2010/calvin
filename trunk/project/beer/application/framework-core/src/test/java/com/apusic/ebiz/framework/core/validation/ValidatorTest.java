package com.apusic.ebiz.framework.core.validation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apusic.ebiz.framework.core.DummyUser;


public class ValidatorTest {

	private Validator<DummyUser> validator;

	@Before
	public void setup(){
		validator = new MockUserValidator();
	}

	@Test
	public void validate(){
		DummyUser user = new DummyUser();
		user.setName("name");
		ValidationReport validationReport = validator.validate(user);
		assertNotNull(user);
		assertTrue(validationReport.getSeverity() == ValidationReport.Severity.ERROR);
	}
}
