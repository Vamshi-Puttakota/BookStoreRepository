package com.orderzzz.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.orderzzz.external.entities.Cart;

@FeignClient(name = "CARTSERVICE",  url = "http://localhost:8082/cart-service")
public interface CartServiceExternal {

	@GetMapping("/getCart")
	Cart getCartById(@RequestHeader("userInfo") String cartId);
	
	@GetMapping("/delete-cart")
	public String deleteCart(@RequestHeader("userInfo") String customerId);
	
	
}
