package com.apusic.ebiz.model.codegen.annotation;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 每个field的对应翻译后的bean
 * 
 * @author xiayong
 * 
 */
public class FieldViewBean {
	private String name;
	//@SuppressWarnings("unused")
	//private Field field;
	private Class<?> type;
	private FieldViewType viewType;
	private String label;
	boolean needDisplay;

	boolean require;
	boolean sortable;
	private int min;
	private int max;
	
	private int width;

	public FieldViewBean(Field field) {
		//this.field = field;
		type = field.getType();
		name = field.getName();
		processFieldViewAnnotation(field);
		processFieldValidatorAnnotation(field);
	}

	private void processFieldValidatorAnnotation(Field field) {
		NotBlank notBlank = field.getAnnotation(NotBlank.class);
		NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
		NotNull notNull= field.getAnnotation(NotNull.class);
		if (notBlank != null || notEmpty != null || notNull != null) {
			needDisplay = true;
			require = true;
		}

		Size size = field.getAnnotation(Size.class);
		if (size != null) {
			min = size.min();
			max = size.max();
		}
		//TODO,这可以增加更多对标记的支持
		Column column = field.getAnnotation(Column.class);
		if (column != null) {
			if (max == 0) {
				max = column.length()/2;
			}
			if (column.nullable() == false) {
				require = true;
			}
		}
		
		if(min>0) {
			require = true;
		}
	}

	private void processFieldViewAnnotation(Field field) {
		FieldView annotation = field.getAnnotation(FieldView.class);
		viewType = annotation.type();
		label = annotation.label();
		needDisplay = annotation.needDisplay();
		width=annotation.width();
		sortable=annotation.sortable();

		if (StringUtils.isEmpty(label)) {
			label = name;
		}
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public FieldViewType getViewType() {
		return viewType;
	}

	public String getLabel() {
		return label;
	}

	public boolean isNeedDisplay() {
		return needDisplay;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Override
	public String toString() {
		return "FieldViewBean [fieldName=" + name + ", type=" + type + ", viewType=" + viewType + ", label=" + label
				+ ", needDisplay=" + needDisplay + ", min=" + min + ", max=" + max + "]";
	}

	public boolean isRequire() {
		return require;
	}

	public boolean isSortable() {
		return sortable;
	}

	public int getWidth() {
		return width;
	}
}
