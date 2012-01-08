package com.xia.quartz.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class JAASTest {

	/**
	 * @param args
	 * @throws LoginException
	 */
	public static void main(String[] args) throws LoginException {
		// TextCallbackHandler callback=new TextCallbackHandler();
		LoginContext context = new LoginContext("Sample", 
				new UserNamePasswordCallbackHandler("xiayong", "pwd"));
		// context.getSubject().
		context.login();
		Subject subject = context.getSubject();
		System.out.println("Login successful");
		// System.out.println(subject.getPrincipals());
		
		String s=Subject.doAsPrivileged(subject, new MyPrivilegedAction(), null);
	}

}

class UserNamePasswordCallbackHandler implements CallbackHandler {
	String userName;
	String password;

	public UserNamePasswordCallbackHandler(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		// TODO Auto-generated method stub
		System.out.println("get");

		for (Callback callback : callbacks) {
			if (callback instanceof NameCallback) {
				((NameCallback) callback).setName(userName);
			} else if (callback instanceof PasswordCallback) {
				((PasswordCallback) callback).setPassword(password.toCharArray());
			}
		}
	}

}