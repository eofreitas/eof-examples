package br.com.eof.examples.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {	

	private static final long serialVersionUID = 3805298326141552175L;

	public NotFoundException() {
		
	}
	
	public NotFoundException(String msg) {
		super(msg);
	}
}
