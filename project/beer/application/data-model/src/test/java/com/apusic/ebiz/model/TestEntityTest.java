package com.apusic.ebiz.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.apusic.ebiz.model.foundation.TestEntity;
@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class TestEntityTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	Validator validator;
	@Test
	public void invokeValidate() {
		TestEntity model=new TestEntity();
		try { 
			model.getClass().getMethod("validate", Validator.class).invoke(model,validator);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
