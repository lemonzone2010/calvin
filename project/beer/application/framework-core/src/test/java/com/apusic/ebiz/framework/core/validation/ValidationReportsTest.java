package com.apusic.ebiz.framework.core.validation;

import static org.junit.Assert.*;
import org.junit.Test;

public class ValidationReportsTest {

	@Test
	public void hasErrors(){
		ValidationReport validationReport = new ValidationReport("Err-001", ValidationReport.Severity.ERROR);
		ValidationReports validationReports = new ValidationReports();
		validationReports.add(validationReport);
		boolean hasError = validationReports.hasError();
		boolean hasWarning = validationReports.hasWarning();
		boolean hasInfo = validationReports.hasInfo();

		assertTrue(hasError);
		assertFalse(hasWarning);
		assertFalse(hasInfo);

		ValidationReports anotherValidationReports = new ValidationReports();
		ValidationReport anotherValidationReport = new ValidationReport("Warn-001", ValidationReport.Severity.WARNING);
		anotherValidationReports.add(anotherValidationReport);
		anotherValidationReports.add(validationReports);

		hasError = anotherValidationReports.hasError();
		hasWarning = anotherValidationReports.hasWarning();
		hasInfo = anotherValidationReports.hasInfo();

		assertTrue(hasError);
		assertTrue(hasWarning);
		assertFalse(hasInfo);
	}

	@Test
	public void validationReportsIsEmpty(){
		ValidationReports validationReports = new ValidationReports();
		boolean hasError = validationReports.hasError();
		assertFalse(hasError);
	}

}
