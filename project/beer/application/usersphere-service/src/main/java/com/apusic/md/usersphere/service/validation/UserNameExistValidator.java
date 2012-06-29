package com.apusic.md.usersphere.service.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.Validator;
import com.apusic.ebiz.smartorg.service.UserService;

@Service("usersphere_UserNameExistValidator")
public class UserNameExistValidator implements Validator<String> {

	@Autowired
	private UserService userService;

	public ValidationReport validate(String toValidate) {

		if(StringUtils.isBlank(toValidate)){
			return new ValidationReport("UserSphere-Err-002",ValidationReport.Severity.ERROR);
		}
		if(userService.userExists(toValidate)){
			return new ValidationReport("UserSphere-Err-003",ValidationReport.Severity.ERROR);
		}
		return new ValidationReport("UserSphere-Info-002",ValidationReport.Severity.INFO);
		//return null;
	}
}
