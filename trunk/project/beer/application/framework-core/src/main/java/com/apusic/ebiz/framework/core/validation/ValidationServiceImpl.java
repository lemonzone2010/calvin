package com.apusic.ebiz.framework.core.validation;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidationServiceImpl implements ValidationService {

	private List<String> validatorConfigs;

	@Autowired
	private ValidatorFactory validatorFactory;

	public ValidationReports validate(Object o) {
		if (CollectionUtils.isEmpty(validatorConfigs)) {
			return null;
		}

		ValidationReports reports = new ValidationReports();

		for (String validatorConfig : validatorConfigs) {
			String[] config = StringUtils.split(validatorConfig, ",");
			String field = "";
			if (config.length > 1) {
				field = config[1];
			}
			ValidationReport validationReport = ValidationUtils
					.invokeValidator(validatorFactory.getValidator(config[0]),
							field, o);
			if (validationReport != null){
				reports.add(validationReport);
			}

		}
		return reports;
	}

	public void setValidatorConfigs(List<String> validatorConfigs) {
		this.validatorConfigs = validatorConfigs;
	}

}
