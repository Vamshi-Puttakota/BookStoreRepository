package com.customers.exceptions;

public class UserExsistsException extends RuntimeException{

	public UserExsistsException() {
		super("User already exsists with the phone number entered");
		// TODO Auto-generated constructor stub
	}

	public UserExsistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
