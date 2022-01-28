package com.abis.app.todoApplication.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {

		ExceptionDetails details = new ExceptionDetails(new Date(), ex.getMessage(), request.getContextPath(),request.getDescription(false));

		return new ResponseEntity(details, HttpStatus.INTERNAL_SERVER_ERROR);
 
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) throws Exception {

		ExceptionDetails details = new ExceptionDetails(new Date(), "User Not Found Exception", request.getDescription(false),ex.getMessage());

		return new ResponseEntity(details, HttpStatus.NOT_FOUND);

	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails details = new ExceptionDetails(new Date(),"Validation Failed", request.getDescription(false),ex.getBindingResult().toString());
		
		return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
	}

}
