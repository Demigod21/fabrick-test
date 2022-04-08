package com.davide.falcone.fabricktest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApplicationHandlerException {

	@ExceptionHandler(FabrickTestException.class)

	public ResponseEntity<String> handleGenericException(FabrickTestException e) {

		return ResponseEntity.status(e.code)
			.body(e.getMessage());
	}
}