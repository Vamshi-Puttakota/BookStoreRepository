package com.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.entities.Cart;
import com.cart.service.CartService;

@RestController
@RequestMapping("/cart-service")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping
	public String addCart(@RequestBody Cart cart)
	{
		cartService.addCart(cart);
		return "Read Data";
	}
	
	@GetMapping("/{cid}")
	public Cart getCartById(@PathVariable String cid)
	{
		return cartService.getCartById(cid);
	}
	
	@GetMapping
	public List<Cart> getAllCart()
	{
		return cartService.getAllCart();
	}
	
	@GetMapping("/delete/{customerId}/{bookId}")
	public Cart deleteBook(@PathVariable String customerId,@PathVariable Integer bookId)
	{
		return cartService.deleteBook(customerId, bookId);
	}
	
	@GetMapping("/delete-cart/{customerId}")
	public String deleteCart(@PathVariable String customerId)
	{
		return cartService.deleteCart(customerId);
	}

}
