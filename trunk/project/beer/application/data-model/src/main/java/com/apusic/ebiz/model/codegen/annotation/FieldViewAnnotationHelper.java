package com.apusic.ebiz.model.codegen.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public class FieldViewAnnotationHelper {
	private final Map<Class<?>, List<FieldViewBean>> infocache = new ConcurrentHashMap<Class<?>, List<FieldViewBean>>();

	public EntityCodeGenBean getBean(Class<?> clazz) {
		EntityCodeGenBean bean = new EntityCodeGenBean();
		bean.setViewFields(getViewFields(clazz));
		bean.setClassName(clazz.getSimpleName());
		bean.setChineseName(clazz.getSimpleName());
		bean.setFullClassName(clazz.getCanonicalName());

		FieldView annotation = clazz.getAnnotation(FieldView.class);
		if (null != annotation) {
			if (StringUtils.isNotBlank(annotation.label())) {
				bean.setChineseName(annotation.label());
			}
			if (StringUtils.isNotBlank(annotation.subpackage())) {
				bean.setSubpackage(annotation.subpackage());
			}
		}

		return bean;
	}

	private List<FieldViewBean> getViewFields(Class<?> clazz) {
		List<FieldViewBean> fields = infocache.get(clazz);
		if (fields == null) {
			synchronized (infocache) {
				infocache.put(clazz, fields = collectInfo(clazz));
			}
		}
		return fields;
	}

	private List<FieldViewBean> collectInfo(Class<?> clazz) {
		List<FieldViewBean> fields = new ArrayList<FieldViewBean>();
		Class<?> superClazz = clazz;
		ArrayList<Field> members = new ArrayList<Field>();
		while (superClazz != null && superClazz != Object.class) {
			members.addAll(Arrays.asList(superClazz.getDeclaredFields()));
			superClazz = superClazz.getSuperclass();
		}
		for (Field member : members) {
			if (member.isAnnotationPresent(FieldView.class)) {
				member.setAccessible(true);
				fields.add(new FieldViewBean(member));
			}
		}
		return fields;
	}

}
