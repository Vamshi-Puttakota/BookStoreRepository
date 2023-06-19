package com.customers.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.customers.dao.CustomerRepository;
import com.customers.dto.AuthRequest;
import com.customers.dto.UserCredential;
import com.customers.entities.Customers;

import com.customers.exceptions.CustomerNotFoundException;
import com.customers.exceptions.GlobalExceptionHandler;
import com.customers.exceptions.InvalidCredentialsException;
import com.customers.exceptions.UserExsistsException;
import com.customers.external.JwtClient;
import com.customers.payload.ApiResponse;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private JwtClient jwt;

	public ResponseEntity<ApiResponse> getCustomerDetailsByPhone(@RequestHeader("customerId") String phn) {

		Optional<Customers> cust = customerRepository.findById(phn);
		if (cust.isEmpty()) {
			throw new CustomerNotFoundException("No customer found with the phn: " + phn);
		}
		Customers customer = cust.get();
		Customers display = new Customers();
		display.setCustomerAddress(customer.getCustomerAddress());
		display.setCustomerEmail(customer.getCustomerEmail());
		display.setCustomerName(customer.getCustomerName());
		display.setCustomerPhone(customer.getCustomerPhone());
		display.setCustomerPassword("xxxxxxxxx");

		ApiResponse response = ApiResponse.builder().message("Record Retrived Successfully").success(true)
				.status(HttpStatus.FOUND).result(display).build();
		return new ResponseEntity<>(response, HttpStatus.FOUND);

	}

	public ResponseEntity<ApiResponse> addCustomers(Customers customer) {
		Customers custo = null;

		Optional<Customers> cust = customerRepository.findById(customer.getCustomerPhone());
		if (!cust.isEmpty()) {
			custo = cust.get();
		}

		if (custo != null)
			throw new UserExsistsException();
		else {
			customerRepository.save(customer);

			UserCredential user = new UserCredential();
			user.setName(customer.getCustomerName());
			user.setCustomerId(customer.getCustomerPhone());
			user.setPassword(customer.getCustomerPassword());
			jwt.addNewUser(user);

			Customers display = new Customers();
			display.setCustomerAddress(customer.getCustomerAddress());
			display.setCustomerEmail(customer.getCustomerEmail());
			display.setCustomerName(customer.getCustomerName());
			display.setCustomerPhone(customer.getCustomerPhone());
			display.setCustomerPassword("xxxxxxxxx");

			ApiResponse response = ApiResponse.builder().message("Customer registered Successfully").success(true)
					.status(HttpStatus.ACCEPTED).result(display).build();
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

		}

	}

	public ResponseEntity<ApiResponse> logIn(AuthRequest customer) {

		try {
			ApiResponse response = ApiResponse.builder().message("Customer Logged in Successfully").success(true)
					.status(HttpStatus.FOUND).result(jwt.getToken(customer)).build();
			return new ResponseEntity<>(response, HttpStatus.FOUND);
			
			
		} catch (Exception e) {
			throw new InvalidCredentialsException();
		}

	}

}
