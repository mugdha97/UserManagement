package com.bridgelabz.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.usermanagement.model.Response;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(CustomException.UserEmailAlreadyExist.class)
	public ResponseEntity<Response> userEmailAlreadyExist(CustomException.UserEmailAlreadyExist e)
	{
		return new ResponseEntity<Response>(new Response(200,e.getMessage(), null), HttpStatus.FOUND);
	}
	@ExceptionHandler(CustomException.UserNotFound.class)
	public ResponseEntity<Response> userNotFound(CustomException.UserNotFound e)
	{
		return new ResponseEntity<Response>(new Response(200,e.getMessage(), null), HttpStatus.FOUND);
	}
	@ExceptionHandler(CustomException.UserNotVerified.class)
	public ResponseEntity<Response> userNotVerified(CustomException.UserNotVerified e)
	{
		return new ResponseEntity<Response>(new Response(200,e.getMessage(), null), HttpStatus.FOUND);
	}
	@ExceptionHandler(CustomException.Password.class)
	public ResponseEntity<Response> Password(CustomException.Password e)
	{
		return new ResponseEntity<Response>(new Response(200,e.getMessage(), null), HttpStatus.FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> Empty( MethodArgumentNotValidException e)
	{
		return new ResponseEntity<Response>(new Response(200,"EMPTY STRING", null), HttpStatus.FOUND);
	}
}
