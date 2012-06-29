package com.apusic.ebiz.datamanagement.service.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apusic.ebiz.datamanagement.service.ConfigurationDataService;
import com.apusic.ebiz.framework.core.validation.ValidationReport;
import com.apusic.ebiz.framework.core.validation.Validator;

@Service("datamanagement_keyExistValidator")
public class KeyExistValidator implements Validator<String> {

	@Autowired
	private ConfigurationDataService configurationDataService;

	public ValidationReport validate(String toValidate) {
		if(StringUtils.isBlank(toValidate)){
			return new ValidationReport("DataManagement-Err-003", ValidationReport.Severity.ERROR);
		}
		String key = null;
		try{
			key = configurationDataService.getValueByKey(toValidate);
		}catch (Exception e) {
		}
		if(key!=null){
			return new ValidationReport("DataManagement-Err-004", ValidationReport.Severity.ERROR);
		}
		return new ValidationReport("DataManagement-Info-002", ValidationReport.Severity.INFO);
	}

}
