package com.apusic.ebiz.framework.core.dao;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.lang.StringUtils;

public class QueryMethodInterceptor implements MethodInterceptor {

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		if (method.getAnnotation(UseProxy.class) == null) {
			return proxy.invokeSuper(obj, args);
		}
		QueryObject query = (QueryObject) obj;

		if (!(args[0] instanceof String)) {
			return null;
		}
		for (int i=1;i<args.length;i++) {
			Object o=args[i];
			if (o == null || (o instanceof String && StringUtils.isBlank((String) o))) {
				return null;
			}
			if (query.isKey(o.toString())) {
				Object value = query.getParameterValue(query.getKey(o.toString()));
				if (value == null){
					return null;
				}
				if ((value instanceof String) && (StringUtils.isBlank(value.toString()))){
					return null;
				}
			}
		}
		args[0] =  createAlias((String) args[0], query);
		return proxy.invokeSuper(obj, args);
	}

	private String createAlias(String propertyName, QueryObject query) {
		final String suffix="_m";		//加个后缀,区别于hibernate自己在sql中生成的
		if (StringUtils.contains(propertyName, ".")) {
			String[] properties = StringUtils.split(propertyName, ".");
			StringBuffer PropertyName = new StringBuffer(properties[0]);
			StringBuffer alienName = new StringBuffer(PropertyName);
			query.createAlias(PropertyName.toString(), alienName.toString());

			for (int i = 1; i < properties.length-1; i++) {
				PropertyName = new StringBuffer(alienName).append(".").append(properties[i]);
				alienName = new StringBuffer(properties[i]).append(suffix);
				query.createAlias(PropertyName.toString(), alienName.toString());
			}
			return alienName.append(".").append(properties[properties.length-1]).toString();
		}
		return propertyName;
	}
}
