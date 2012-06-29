package com.apusic.md.usersphere.service.validation;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.Validator;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class UserNameExistValidatorTest{

	@Autowired
	@Qualifier("usersphere_UserNameExistValidator")
	private Validator validator;

	@Autowired
	@Qualifier("smartorg_UserService")
	private UserService userService;

	@Test
	public void validateEmpty() {
		String empty = "";
		ValidationReport report = validator.validate(empty);
		Assert.assertEquals("UserSphere-Err-002", report.getCode());
		Assert.assertTrue(ValidationReport.Severity.ERROR == report.getServerity());
	}

	@Test
	public void validateNotUserExists(){
		//Mock the hebaviors of userService
		ValidationReport report = validator.validate("fff");
		Assert.assertEquals("UserSphere-Info-002", report.getCode());
		Assert.assertTrue(ValidationReport.Severity.INFO == report.getServerity());

	}

	@Test
	@Transactional
	public void validateUserExist(){
		//Register the user
		User user = new User();
		user.setName("hialex11");
		user.setPassword("hialex11");
		userService.addUser(user);

		ValidationReport report = validator.validate("hialex11");
		Assert.assertEquals("UserSphere-Err-003", report.getCode());
		Assert.assertTrue(ValidationReport.Severity.ERROR == report.getServerity());

	}
}
