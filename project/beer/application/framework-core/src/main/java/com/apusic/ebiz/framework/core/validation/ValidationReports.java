package com.apusic.ebiz.framework.core.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class ValidationReports {

	private List<ValidationReport> validationReports;

	public void add(ValidationReports validationReports){
		if (validationReports==null || CollectionUtils.isEmpty(validationReports.getValidationReports())){
			return;
		}

		if (this.validationReports == null){
			this.validationReports = new ArrayList<ValidationReport>();
		}

		this.validationReports.addAll(validationReports.getValidationReports());
	}

	public void add(ValidationReport validateReport) {
		if (validationReports == null){
			validationReports = new ArrayList<ValidationReport>();
		}

		validationReports.add(validateReport);
	}

	public List<ValidationReport> getValidationReports(){
		return this.validationReports;
	}

	public boolean hasError(){
		return CollectionUtils.isNotEmpty(this.getErrorValidationReports());
	}

	public boolean hasWarning(){
		return CollectionUtils.isNotEmpty(this.getWarningValidationReports());
	}


	public boolean hasInfo(){
		return CollectionUtils.isNotEmpty(this.getInfoValidationReports());
	}

	public List<ValidationReport> getErrorValidationReports(){
		return this.getValidateReportBySeverity(ValidationReport.Severity.ERROR);
	}

	public List<ValidationReport> getWarningValidationReports(){
		return this.getValidateReportBySeverity(ValidationReport.Severity.WARNING);
	}

	public List<ValidationReport> getInfoValidationReports(){
		return this.getValidateReportBySeverity(ValidationReport.Severity.INFO);
	}

	private  List<ValidationReport> getValidateReportBySeverity(ValidationReport.Severity severity){
		List<ValidationReport> reports  = new  ArrayList<ValidationReport>();

		if (CollectionUtils.isEmpty(validationReports)){
			return reports;
		}

		for (ValidationReport validationReport : validationReports) {
			if (validationReport.getSeverity() == severity){
				reports.add(validationReport);
			}
		}
		return reports;
	}


}
