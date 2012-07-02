package com.xia.search.solr.util;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;

public class JasonUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	public static <T> T toObjectFromJson(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Jason 转换出错:" + e.getMessage(), e);
		}
	}

	public static String toJsonString(Object obj) {
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, obj);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Jason 转换出错:" + e.getMessage(), e);
		}
	}
}
