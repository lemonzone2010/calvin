package org.jasig.cas.authentication.handler;

public class UserDisabledException extends AuthenticationException {

	public static final UserDisabledException ERROR = new UserDisabledException();

	private static final String CODE = "error.authentication.user.disabled";

	public UserDisabledException() {
		super(CODE);
	}

	public UserDisabledException(String code) {
		super(code);
	}

	public UserDisabledException(String code,Throwable throwable){
		super(code, throwable);
	}
}
