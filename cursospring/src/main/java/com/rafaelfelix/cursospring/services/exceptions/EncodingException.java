package com.rafaelfelix.cursospring.services.exceptions;

public class EncodingException extends RuntimeException{

	private static final long serialVersionUID = -7639748388483943718L;
	
	public EncodingException(String msg) {
		super(msg);
	}
	
	public EncodingException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
