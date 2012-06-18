package com.apusic.ebiz.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.apusic.ebiz.framework.core.context.ApplicationContextHolder;
import com.apusic.ebiz.model.foundation.TestEntity;

@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class TestEntityTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	Validator validator;

	@Test
	public void invokeValidate() {
		new ApplicationContextHolder().setApplicationContext(applicationContext);
		TestEntity model = new TestEntity();
		try {
			model.getClass().getMethod("validate", Validator.class).invoke(model, validator);
		} catch (Exception e) {
			Assert.assertTrue(e.getCause().getMessage().indexOf("不能为空")>-1);
			//Assert.assertEquals("[Values]不能为空:[姓名]名字 不能为空", e.getCause().getMessage());
		}
		try {
			model.validate();
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().indexOf("不能为空")>-1);
			//Assert.assertEquals("[Values]不能为空:[姓名]名字 不能为空", e.getCause().getMessage());
		}
	}
}
