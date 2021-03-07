package com.poseidon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class DuplicatedResourceException extends Exception{

	private static final long serialVersionUID = 1L;

	public DuplicatedResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedResourceException(String message) {
		super(message);
	}
	
	

}
