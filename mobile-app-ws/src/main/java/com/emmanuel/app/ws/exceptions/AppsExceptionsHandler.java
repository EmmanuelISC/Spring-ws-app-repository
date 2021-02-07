package com.emmanuel.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.emmanuel.app.ws.ui.model.response.ErrorMessaage;

@ControllerAdvice
public class AppsExceptionsHandler {
	@ExceptionHandler(value = {UserServiceException.class})
	public ResponseEntity<Object> handlerUserServiceException(UserServiceException ex, WebRequest request)
	{
		ErrorMessaage errorMessage = new ErrorMessaage(new Date(), ex.getMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handlerOtherExceptions(Exception ex, WebRequest request)
	{
		ErrorMessaage errorMessage = new ErrorMessaage(new Date(), ex.getMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
		

}
