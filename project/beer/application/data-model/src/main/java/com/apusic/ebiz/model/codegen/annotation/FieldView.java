package com.apusic.ebiz.model.codegen.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 页面将要展示哪些field，[<b>须定义在属性上,类上</b>],暂时支持@notNull,@NotBank,@Size,
 * 已支持@column要nullable,size属性，所以可以不重复定义,这里指客户端校验，如果要服务端校验，那么加上@NotNull
 * @author xiayong
 * 
 */
@Target({ FIELD, TYPE })
@Retention(RUNTIME)
public @interface FieldView {
	/**
	 * 在页面的显示类型，如是时间类型，纯数字
	 */
	FieldViewType type() default FieldViewType.STRING;

	/**
	 * 字段对应的LABEL，将存在于resource.properties
	 */
	String label() default "";

	/**
	 * 模块名，比如它属于基础模块foundation,它包里展现为子包
	 */
	String subpackage() default "";

	/**
	 * 是否在页面显示,默认显示
	 */
	boolean needDisplay() default true;

	/**
	 * 是否在页面要排序，默认不排
	 */
	boolean sortable() default false;
	
	int width() default 150;
}
