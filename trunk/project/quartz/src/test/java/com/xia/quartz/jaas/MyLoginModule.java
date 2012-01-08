package com.xia.quartz.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class MyLoginModule implements LoginModule{
	  private boolean isAuthenticated = false;
	  private CallbackHandler callbackHandler;
	  private Subject subject;
	  MyPrincipal principal;
	  public void initialize(Subject subject, CallbackHandler callbackHandler,
	      Map sharedState, Map options) {
	    this.subject = subject;
	    this.callbackHandler = callbackHandler;
	  }
/*
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		// TODO Auto-generated method stub
		System.out.println("init");
	}
*/
	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		NameCallback nameCallback=new NameCallback("userName");
		PasswordCallback passwordCallback=new PasswordCallback("password",false);
		Callback[] callbacks=new Callback[] {nameCallback,passwordCallback};
		try {
			callbackHandler.handle(callbacks);
			String name = nameCallback.getName();
			String password=String.valueOf(passwordCallback.getPassword());
			System.out.println("Login Name:"+name +password);
			if("xiayong".equals(name)) {
				isAuthenticated=true;
				principal=new MyPrincipal(name);
			}else {
				throw new LoginException("User Name is wrong.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("login");
		return isAuthenticated;
	}

	@Override
	public boolean commit() throws LoginException {
		System.out.println("commit");
		if(isAuthenticated) {
			subject.getPrincipals().add(principal);
		}else {
			throw new LoginException("Login failed");
		}
		// TODO Auto-generated method stub
		return isAuthenticated;
	}

	@Override
	public boolean abort() throws LoginException {
		System.out.println("abort");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("logout");
		subject.getPrincipals().remove(principal);
		return false;
	}
	
}