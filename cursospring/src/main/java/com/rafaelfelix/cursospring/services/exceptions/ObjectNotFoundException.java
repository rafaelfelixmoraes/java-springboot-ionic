package com.rafaelfelix.cursospring.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7639748388483943718L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
