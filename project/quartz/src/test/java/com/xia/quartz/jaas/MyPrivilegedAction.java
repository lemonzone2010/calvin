package com.xia.quartz.jaas;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;

public class MyPrivilegedAction implements PrivilegedAction<String>{

	@Override
	public String run() {
		AccessControlContext context=AccessController.getContext();
		Subject s=Subject.getSubject(context);
		System.out.println(s.getPrincipals());
		return null;
	}

}
