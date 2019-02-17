package com.rafaelfelix.cursospring.resources.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("errors")
	private List<FieldMessage> listMessage = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Date timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getListMessage() {
		return listMessage;
	}

	public void addError(String fieldName, String message) {
		listMessage.add(new FieldMessage(fieldName, message));
	}

}
