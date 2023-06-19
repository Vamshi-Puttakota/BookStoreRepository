package com.cart.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.entities.Book;
import com.cart.entities.Cart;
import com.cart.entities.Items;
import com.cart.external.BookServiceExternal;
import com.cart.repository.CartRepository;

@Service
public class CartService {

	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private BookServiceExternal bookServiceExternal;
	
	public Cart addCart(Cart cart)
	{	
		Integer totalprice = 0;
		try 
		{

			Cart c = getCartById(cart.getCustomerId());
			c.getItems().addAll(cart.getItems());
			
			  totalprice = c.getTotalPrice(); 
			  List<Items> items = cart.getItems();
			  
			  Iterator<Items> iterator = items.iterator();
			  while (iterator.hasNext()) {
				  
				  
			  Items item = iterator.next(); 
			  
			  totalprice += (item.getBookPrice() * item.getQuantity()); 
			  }
			  c.setTotalPrice(totalprice);
			 
			return cartRepository.save(c);
		}
		
		
		catch(Exception ex){
			
			  List<Items> items = cart.getItems();
			  Iterator<Items> iterator = items.iterator(); 
			  while (iterator.hasNext()) {
			  Items item = iterator.next(); 
			  System.out.println(item.getBookId()+"..................");
			 
			  totalprice += (item.getBookPrice() * item.getQuantity()); 

			  }
			  

			  cart.setTotalPrice(totalprice);
			 
			return cartRepository.save(cart);
		}
		
		
	}
	
	
	public Cart deleteBook(String cid, Integer bid)
	{
		Cart c = getCartById(cid);
		Integer totalPrice = c.getTotalPrice();
		
		List<Items> items= c.getItems();
		Iterator<Items> iterator = items.iterator();
		while (iterator.hasNext()) {
		    Items item = iterator.next();
		   
		    if (item.getBookId().equals(bid)) {
		    	
		        iterator.remove(); 
		    }
		}
		cartRepository.save(c);
		return c;
	}
	
	public String deleteCart(String cid)
	{
		Cart c = getCartById(cid);
		cartRepository.delete(c);
		return "customer :" + cid + " cart is cleared";
	}
	
	public Cart getCartById(String cid)
	{
		return cartRepository.findByCustomerId(cid);
	}
	
	
	public List<Cart> getAllCart()
	{
		return cartRepository.findAll();
	}
	
	
}
