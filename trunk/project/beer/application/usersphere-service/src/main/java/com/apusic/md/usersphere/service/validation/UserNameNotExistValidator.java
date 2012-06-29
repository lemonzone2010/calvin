package com.apusic.md.usersphere.service.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.Validator;
import com.apusic.ebiz.smartorg.service.UserService;

@Service("usersphere_UserNameNotExistValidator")
public class UserNameNotExistValidator implements Validator<String> {

	@Autowired
	@Qualifier("smartorg_UserService")
	private UserService userService;

	public ValidationReport validate(String toValidate) {

		//如果用户名为空
		if(StringUtils.isBlank(toValidate)){
			return new ValidationReport("UserSphere-Err-002",ValidationReport.Severity.ERROR);
		}
		//如果用户名不存在
		if(!userService.userExists(toValidate)){
			return new ValidationReport("UserSphere-Err-004",ValidationReport.Severity.ERROR);
		}
		return null;
	}
}
