package com.apusic.ebiz.framework.core.validation;


public interface ValidationService<T> {

	ValidationReports validate(T o);

}
