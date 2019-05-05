package com.rafaelfelix.cursospring.services.exceptions;

public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = -7639748388483943718L;
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
