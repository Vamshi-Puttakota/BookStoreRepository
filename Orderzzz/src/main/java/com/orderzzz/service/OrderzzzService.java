package com.orderzzz.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderzzz.entities.OrderzzzItems;
import com.orderzzz.external.BookServiceExternal;
import com.orderzzz.external.CartServiceExternal;
import com.orderzzz.external.entities.Book;
import com.orderzzz.external.entities.Cart;
import com.orderzzz.payload.ApiResponse;
import com.orderzzz.entities.Orderzzz;

import com.orderzzz.repository.OrderzzzRepository;

@Service
public class OrderzzzService {

	
	@Autowired
	private OrderzzzRepository orderzzzRepository;
	
	@Autowired
	private CartServiceExternal cartServiceExternal;
	
	@Autowired
	private BookServiceExternal bookServiceExternal;
	
	
	public Orderzzz addOrderzzz(Orderzzz orderzzz)
	{	
		String randomOrderId =  UUID.randomUUID().toString();
			
		orderzzz.setOrderzzzId(randomOrderId);
		return orderzzzRepository.save(orderzzz);
	}
	
	
	public ResponseEntity<ApiResponse> getOrderzzzByCustomerId(String cid)
	{
		
		ApiResponse response = ApiResponse.builder().message("Order retrieved").success(true)
				.status(HttpStatus.ACCEPTED).result(orderzzzRepository.findByCustomerId(cid)).build();
		return new ResponseEntity <>(response, HttpStatus .ACCEPTED);
	}
	
	public ResponseEntity<ApiResponse> getOrderzzzById(String oid)
	{
		ApiResponse response = ApiResponse.builder().message("Order retrieved").success(true)
				.status(HttpStatus.ACCEPTED).result(orderzzzRepository.findById(oid).get()).build();
		return new ResponseEntity <>(response, HttpStatus .ACCEPTED);

	}
	
	public ResponseEntity<ApiResponse> getAllOrderzzz()
	{
		ApiResponse response = ApiResponse.builder().message("All orders by you").success(true)
				.status(HttpStatus.ACCEPTED).result(orderzzzRepository.findAll()).build();
		return new ResponseEntity <>(response, HttpStatus .ACCEPTED);
		
	}
	
	public ResponseEntity<ApiResponse> addOrderByMethod(String cust)
	{
		Cart cart = cartServiceExternal.getCartById(cust);
		System.err.println(cart);
		List<OrderzzzItems> items = cart.getItems();
		
		Orderzzz order = new Orderzzz();
		
		String randomOrderId =  UUID.randomUUID().toString();
		
		order.setOrderzzzId(randomOrderId);
		order.setCustomerId(cart.getCustomerId());
		Integer bill= 0;
			
		  Iterator<OrderzzzItems> iterator = items.iterator();
		  while(iterator.hasNext()) 
		  { OrderzzzItems item = iterator.next();
		  	bookServiceExternal.orderBook(item.getBookId(), item.getQuantity());
		  	bill = bill + (item.getBookPrice() * item.getQuantity());
		  	item.setOrderzzz(order);
		  	order.getOrderzzzItems().add(item);
		  
		  }
		  
		  cartServiceExternal.deleteCart(cust);
		  order.setTotalBill(bill);
		  System.out.println("E-Mail:..Order has been placed with OrderID: "+order.getOrderzzzId());
		  
		  ApiResponse response = ApiResponse.builder().message("Order Placed").success(true)
					.status(HttpStatus.ACCEPTED).result(orderzzzRepository.save(order)).build();
			return new ResponseEntity <>(response, HttpStatus .ACCEPTED);
	}
	
}
