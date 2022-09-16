package com.ram.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp, WebRequest wr){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getAllErrors().get(0).getDefaultMessage());
	}
	
	@ExceptionHandler
	public final ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exp, WebRequest wr){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
	}
	
	@ExceptionHandler
	public final ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exp, WebRequest wr){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
	}
}
