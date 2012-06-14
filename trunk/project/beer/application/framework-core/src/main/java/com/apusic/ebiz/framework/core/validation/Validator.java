package com.apusic.ebiz.framework.core.validation;

public interface Validator<T> {
	ValidationReport validate(T toValidate);
}
