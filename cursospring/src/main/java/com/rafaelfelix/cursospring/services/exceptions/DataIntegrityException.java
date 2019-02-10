package com.rafaelfelix.cursospring.services.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = -7639748388483943718L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
