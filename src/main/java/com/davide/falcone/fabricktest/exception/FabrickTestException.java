package com.davide.falcone.fabricktest.exception;

import org.springframework.http.HttpStatus;

public class FabrickTestException extends Exception{
	HttpStatus code;
	String message;

	public FabrickTestException(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}

	public FabrickTestException(String message, HttpStatus code, String message1) {
		super(message);
		this.code = code;
		this.message = message1;
	}

	public FabrickTestException(String message, Throwable cause, HttpStatus code, String message1) {
		super(message, cause);
		this.code = code;
		this.message = message1;
	}

	public FabrickTestException(Throwable cause, HttpStatus code, String message) {
		super(cause);
		this.code = code;
		this.message = message;
	}

	public FabrickTestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus code, String message1) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code = code;
		this.message = message1;
	}
}
