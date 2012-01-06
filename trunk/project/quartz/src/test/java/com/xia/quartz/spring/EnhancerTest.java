package com.xia.quartz.spring;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class EnhancerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClassProxy.get().hello("xia");

		MyFactory my = (MyFactory) KeyFactory.create(MyFactory.class);
		Object newInstance = my.newInstance(1, "--x");
		System.out.println(newInstance);
	}

}

interface MyFactory {
	public Object newInstance(int a, String b);
}

class MyClassProxy {
	public static MyClass get() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(MyClass.class);
		enhancer.setCallback(new MyClassMethodInterceptorImpl());
		return (MyClass) enhancer.create();
	}
}

class MyClass {

	public void hello(String name) {
		System.out.println("Hello," + name);
	}
}

class MyClassMethodInterceptorImpl implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		MyClass my = (MyClass) obj;
		System.out.println(" before Method Geted:" + "," + Arrays.toString(args));
		args[0] = args[0] + "_Yong";
		Object ret = proxy.invokeSuper(obj, args);
		System.out.println("after Method,");
		return ret;
	}

}