package com.apusic.ebiz.framework.core.validation;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.apusic.ebiz.framework.core.FrameworkRuntimeException;


public class ValidationUtils {

	public static ValidationReport invokeValidator(Validator validator,
			String onField, Object target) {
		Assert.notNull(validator, "Validator must not be null");
		Assert.notNull(target, "Target must not be null");

		if (StringUtils.isBlank(onField)) {
			return validator.validate(target);
		}

		Object toValidate = null;

		try {
			toValidate = PropertyUtils.getNestedProperty(target, onField);
		} catch (IllegalAccessException e) {
			throw new FrameworkRuntimeException("No field " + onField
					+ " object " + target.getClass().getSimpleName(), e);
		} catch (InvocationTargetException e) {
			throw new FrameworkRuntimeException("No field " + onField
					+ " object " + target.getClass().getSimpleName(), e);
		} catch (NoSuchMethodException e) {
			throw new FrameworkRuntimeException("No field " + onField
					+ " object " + target.getClass().getSimpleName(), e);
		} catch (NestedNullException e) {
			throw new FrameworkRuntimeException("No field " + onField
					+ " object " + target.getClass().getSimpleName(), e);
		}

		return validator.validate(toValidate);

	}


}
