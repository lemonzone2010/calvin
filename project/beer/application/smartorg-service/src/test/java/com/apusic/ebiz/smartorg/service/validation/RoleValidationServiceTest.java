package com.apusic.ebiz.smartorg.service.validation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.ValidationReports;
import com.apusic.ebiz.framework.core.validation.ValidationService;
import com.apusic.ebiz.model.user.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class RoleValidationServiceTest {

	@Autowired
	@Qualifier("smartorg_RoleValidationSerivce")
	private ValidationService validationService;

	@Test
	public void testValidation(){
		Role r = new Role();
		ValidationReports validate = validationService.validate(r );
		List<ValidationReport> errorValidationReports = validate.getErrorValidationReports();
		ValidationReport validationReport = errorValidationReports.get(0);
		Assert.assertEquals("SmartOrg-Err-003", validationReport.getCode());
	}
}
