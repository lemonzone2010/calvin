package com.apusic.ebiz.framework.core.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.DummyUser;


@Service("mockUserValidator")
public  class MockUserValidator implements Validator<DummyUser>{

	public ValidationReport validate(DummyUser toValidate) {
		String name = toValidate.getName();
		if (StringUtils.equals(name, "name")){
			return new ValidationReport("SmartOrg-001", ValidationReport.Severity.ERROR);
		}
		return null;
	}
}
