package com.poseidon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class DuplicatedUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicatedUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedUserException(String message) {
		super(message);
	}
	
	

}
