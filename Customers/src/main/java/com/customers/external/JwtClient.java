package com.customers.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.customers.dto.AuthRequest;
import com.customers.dto.UserCredential;

@Service
@FeignClient(url="http://localhost:1111/authentication",name="Jwt-Client")
@ComponentScan
public interface JwtClient {
	
	@PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user);
	@PostMapping("/generattoken")
    public String getToken(@RequestBody AuthRequest authRequest);
	@GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token);

}