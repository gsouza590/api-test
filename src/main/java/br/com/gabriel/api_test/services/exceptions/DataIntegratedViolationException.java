package br.com.gabriel.api_test.services.exceptions;

public class DataIntegratedViolationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegratedViolationException(String message) {
		super(message);
	}
}
