package com.xia.quartz.spring;

import java.util.Properties;

import org.springframework.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderHelperTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("ss", "1111#&&$1111");
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("#", "$");
		String value = helper.replacePlaceholders("t{ss}t", properties);
		System.out.println(value);
	}

}
