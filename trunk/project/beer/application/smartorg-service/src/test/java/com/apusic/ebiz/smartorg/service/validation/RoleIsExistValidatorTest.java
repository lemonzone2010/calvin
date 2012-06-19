package com.apusic.ebiz.smartorg.service.validation;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.model.user.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RoleIsExistValidatorTest {

	@Autowired
	private RoleIsExistValidator roleIsExistValidator;

	@Test
	public void testValidate(){
		Role role = new Role();
		ValidationReport validate = roleIsExistValidator.validate(role);
		Assert.assertEquals("SmartOrg-Err-003", validate.getCode());
		role.setName("test");
		ValidationReport validate2 = roleIsExistValidator.validate(role);
		if(validate2!=null){
			Assert.assertEquals("SmartOrg-004", validate2.getCode());
		}
	}
}
