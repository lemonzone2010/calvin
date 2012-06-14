package com.apusic.ebiz.framework.core.validation;

public interface ValidatorFactory {

	/**
	 * Get the validator by its name.
	 * A validator is a spring managed bean in the spring application context
	 * @param name
	 * @return
	 */
	Validator getValidator(String name);

}
