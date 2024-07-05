package br.com.gabriel.api_test.controllers.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gabriel.api_test.services.exceptions.DataIntegratedViolationException;
import br.com.gabriel.api_test.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> ObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
	}
	
	@ExceptionHandler(DataIntegratedViolationException.class)
	public ResponseEntity<StandardError> dataIntegratedViolationException(DataIntegratedViolationException ex, HttpServletRequest request){
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		
	}
}
