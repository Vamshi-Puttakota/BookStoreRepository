package com.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.entities.Cart;
import com.cart.payload.ApiResponse;
import com.cart.service.CartService;

@RestController
@RequestMapping("/cart-service")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add-books")
	public ResponseEntity<ApiResponse> addCart(@RequestHeader("userInfo") String custId, @RequestBody Cart cart) {
		cart.setCustomerId(custId);
		return cartService.addCart(cart);

	}
	
	@GetMapping("/add-book/{bookId}/{qty}")
	public ResponseEntity<ApiResponse> addBookToCart(@RequestHeader("userInfo") String custId,@PathVariable Integer bookId,@PathVariable Integer qty) {
		
		return cartService.addBookToCart(custId, bookId, qty);

	}
	

	@GetMapping("/getCart")
	public Cart getCartById(@RequestHeader("userInfo") String cid) {
		return cartService.getCartById(cid);
	}

	@GetMapping("/get-all-carts")
	public List<Cart> getAllCart() {
		return cartService.getAllCart();
	}

	@GetMapping("/delete-book/{bookId}")
	public Cart deleteBook(@RequestHeader("userInfo") String customerId, @PathVariable Integer bookId) {
		return cartService.deleteBook(customerId, bookId);
	}

	@GetMapping("/delete-cart")
	public String deleteCart(@RequestHeader("userInfo") String customerId) {
		return cartService.deleteCart(customerId);
	}

}
