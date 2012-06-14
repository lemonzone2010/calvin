package com.apusic.ebiz.framework.core.validation;

import org.springframework.stereotype.Service;


@Service("mockUserNameValidator")
public class MockUserNameValidator implements Validator<String>{

	public ValidationReport validate(String toValidate) {
		ValidationReport validationReport = new ValidationReport(
				"SmartOrg-Err-001", ValidationReport.Severity.ERROR);
		return validationReport;
	}

}
