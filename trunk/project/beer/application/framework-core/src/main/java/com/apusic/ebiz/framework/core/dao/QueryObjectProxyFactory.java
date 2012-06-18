package com.apusic.ebiz.framework.core.dao;

import net.sf.cglib.proxy.Enhancer;

import com.apusic.ebiz.framework.core.model.IdEntity;

public class QueryObjectProxyFactory {

	public static QueryObject getProxy(Class<? extends IdEntity> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(QueryObject.class);
		enhancer.setCallback(new QueryMethodInterceptor());
		return (QueryObject) enhancer.create(new Class[] { Class.class }, new Object[] { clazz });
	}
}
