package com.rafaelfelix.cursospring.resources.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rafaelfelix.cursospring.services.exceptions.DataIntegrityException;
import com.rafaelfelix.cursospring.services.exceptions.EncodingException;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
        	   .body(new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), new Date()));
    }
	
	@ExceptionHandler(DataIntegrityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> dataIntegrity(DataIntegrityException exception) {
		return ResponseEntity.badRequest()
        	   .body(new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), new Date()));
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> validation(MethodArgumentNotValidException exception) {
		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", new Date());
		for(FieldError error : exception.getBindingResult().getFieldErrors()) {
			validationError.addError(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(validationError);
    }
	
	@ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> dataIntegrity(UnsupportedOperationException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        	   .body(new StandardError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), new Date()));
    }
	
	@ExceptionHandler(EncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> objectNotFound(EncodingException exception) {
		return ResponseEntity.badRequest()
        	   .body(new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), new Date()));
    }
}
