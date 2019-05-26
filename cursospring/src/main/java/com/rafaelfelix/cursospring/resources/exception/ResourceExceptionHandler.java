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

import com.rafaelfelix.cursospring.services.exceptions.AuthorizationException;
import com.rafaelfelix.cursospring.services.exceptions.DataIntegrityException;
import com.rafaelfelix.cursospring.services.exceptions.EncodingException;
import com.rafaelfelix.cursospring.services.exceptions.FileException;
import com.rafaelfelix.cursospring.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<?> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
        	   .body(new StandardError(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(DataIntegrityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> dataIntegrity(DataIntegrityException exception, HttpServletRequest request) {
		return ResponseEntity.badRequest()
        	   .body(new StandardError(new Date(), HttpStatus.BAD_REQUEST.value(), "Data Integrity", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<?> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
		ValidationError validationError = new ValidationError(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation Error", exception.getMessage(), request.getRequestURI());
		for(FieldError error : exception.getBindingResult().getFieldErrors()) {
			validationError.addError(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.badRequest().body(validationError);
    }
	
	@ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> unsupportedOperation(UnsupportedOperationException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        	   .body(new StandardError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unsupported Operation", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(EncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> objectNotFound(EncodingException exception, HttpServletRequest request) {
		return ResponseEntity.badRequest()
        	   .body(new StandardError(new Date(), HttpStatus.BAD_REQUEST.value(), "Encoding Error", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<?> authorization(AuthorizationException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
        	   .body(new StandardError(new Date(), HttpStatus.FORBIDDEN.value(), "Authorization Exception", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> file(FileException exception, HttpServletRequest request) {
		return ResponseEntity.badRequest()
        	   .body(new StandardError(new Date(), HttpStatus.BAD_REQUEST.value(), "File Exception", exception.getMessage(), request.getRequestURI()));
    }
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<?> runtime(RuntimeException exception, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    	   .body(new StandardError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Generic Error", exception.getMessage(), request.getRequestURI()));
    }
}
