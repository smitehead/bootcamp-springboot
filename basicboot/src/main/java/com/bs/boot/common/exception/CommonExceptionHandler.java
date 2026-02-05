package com.bs.boot.common.exception;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class CommonExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> illegal(IllegalArgumentException e) {
		Map data=Map.of("message",e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> noSuch(NoSuchElementException e) {
		Map data=Map.of("message",e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
	}
	
}





