package com.apusic.ebiz.smartorg.service.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.Validator;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.smartorg.dao.RoleDao;

@Service("smartorg_RoleIsExistValidator")
public class RoleIsExistValidator implements Validator<Role> {

	@Autowired
	private RoleDao roleDao;

	public ValidationReport validate(Role toValidate) {
		if(StringUtils.isBlank(toValidate.getName())){
			return new ValidationReport("SmartOrg-Err-003",ValidationReport.Severity.ERROR);
		}
		Role role = this.roleDao.getRoleByName(toValidate.getName());
		if(role!=null){
			return new ValidationReport("SmartOrg-Err-004",ValidationReport.Severity.ERROR);
		}
		return null;
	}
}
