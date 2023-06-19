package com.books.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.books.payload.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerCustomerNotFoundException(BookNotFoundException ex)
	{
		String message = ex.getMessage();
		ApiResponse resp = ApiResponse.builder().message(message).success(false).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
	}

	
	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<ApiResponse> handlerUserExsistsException(InvalidQuantityException ex)
	{
		String message = ex.getMessage();
		ApiResponse resp = ApiResponse.builder().message(message).success(false).status(HttpStatus.CONFLICT).build();
		
		return new ResponseEntity<>(resp,HttpStatus.CONFLICT);
	}

}