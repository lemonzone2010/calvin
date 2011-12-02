package com.apusic.soquick;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

public class Query {
	Class clazz;
	String q = "";

	public Query(Class clazz) {
		this.clazz = clazz;
	}

	public Query and(String field, String value) {
		add(field, value, "AND");
		return this;
	}

	public Query or(String field, String value) {
		add(field, value, "OR");
		return this;
	}

	public Query add(String field, String value, String condition) {
		if (StringUtils.isBlank(q)) {
			q += getField(field) + ":" + value;
		} else {
			q += " " + condition + " " + getField(field) + ":" + value;
		}
		return this;
	}

	@Override
	public String toString() {
		return StringUtils.isBlank(q) ? "*:*" : q;
	}

	private String getField(String fieldName) {
		Class superClazz = clazz;
		String tableName = "";
		if (superClazz.isAnnotationPresent(Table.class)) {
			tableName = ((Table) superClazz.getAnnotation(Table.class)).name();
		}
		ArrayList<AccessibleObject> members = new ArrayList<AccessibleObject>();
		while (superClazz != null && superClazz != Object.class) {
			members.addAll(Arrays.asList(superClazz.getDeclaredFields()));
			members.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
			superClazz = superClazz.getSuperclass();
		}
		for (AccessibleObject member : members) {
			if (member.isAnnotationPresent(Column.class)) {
				String name = member.getAnnotation(Column.class).name();
				if (StringUtils.isNotBlank(name) && member instanceof Field
						&& StringUtils.endsWith(((Field) member).getName(), fieldName)) {
					return tableName + "." + name;
				} else {
					// TODO 对方法名的解析标记语言
				}
			}
		}
		return fieldName;
	}

	public Class getQueryClazz() {
		return clazz;
	}

}
