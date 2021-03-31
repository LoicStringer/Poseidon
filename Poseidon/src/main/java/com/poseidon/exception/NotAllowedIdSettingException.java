package com.poseidon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowedIdSettingException extends Exception{

	private static final long serialVersionUID = 1L;

	public NotAllowedIdSettingException() {
		super();
	}

	public NotAllowedIdSettingException(String message) {
		super(message);
	}
	
	

}
