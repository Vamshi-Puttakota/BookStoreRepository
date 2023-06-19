package com.cart.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cart.entities.Book;
import com.cart.entities.Cart;
import com.cart.entities.Items;
import com.cart.external.BookServiceExternal;
import com.cart.payload.ApiResponse;
import com.cart.repository.CartRepository;
import com.cart.repository.ItemsRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BookServiceExternal bookServiceExternal;


	public Cart newOrOldCart(String customerId) {
		Optional<Cart> c = cartRepository.findById(customerId);

		if (c.isEmpty()) {
			Cart cart = new Cart();
			cart.setCustomerId(customerId);
			cart.setTotalPrice(0);
			return cart;
		} else
			return c.get();
	}

	// adding single book using bookId and quantity using pathvariables
	//this method checks for previous occurence of the book in cart before adding
	public ResponseEntity<ApiResponse> addBookToCart(String customerId, Integer bookId, Integer qty)
	{
		Cart c = newOrOldCart(customerId);
		
		Integer totalprice = c.getTotalPrice();
		List<Items> items = c.getItems();
		Integer totalQuantity = qty;
		Book book = bookServiceExternal.getBook(bookId);
		Items oldItem = new Items();
		
		for(Items i: items)
		{
			if(i.getBookId()==bookId)
			{
				totalQuantity += i.getQuantity();
				oldItem=i;
			}
		}
		
		if (book.getBookQuantity() < totalQuantity) {
			System.err.println(" Entered qunatity of " + book.getBookTitle() + " is greater than "
					+ "available please check and enter available quantity");
			
		} else if (totalQuantity <= 0) {
			System.err.println(" You have entered invalid quantity for " + book.getBookTitle());
			

		} else if (book.getBookQuantity() >= totalQuantity)

		{
			totalprice += (book.getBookPrice() * qty);
			if(oldItem.getBookId()!=null)
			{
				oldItem.setQuantity(totalQuantity);
			}
			else
			{
				oldItem.setBookId(bookId);
				oldItem.setBookPrice(book.getBookPrice());
				oldItem.setQuantity(qty);
				oldItem.setCart(c);
				
				items.add(oldItem);
			}
			c.setTotalPrice(totalprice);
		}
		
		ApiResponse response = ApiResponse.builder().message("Book added to cart").success(true)
				.status(HttpStatus.ACCEPTED).result(cartRepository.save(c)).build();
		return new ResponseEntity <>(response, HttpStatus .ACCEPTED);
		
		
		
	}
	
	
	// adding a list of books at once using json of cart object.
	public  ResponseEntity<ApiResponse> addCart(Cart cart) {
		Integer totalprice = 0;

		Cart c = newOrOldCart(cart.getCustomerId());

		totalprice = c.getTotalPrice();
		List<Items> items = cart.getItems();

		Iterator<Items> iterator = items.iterator();
		while (iterator.hasNext()) {

			Items item = iterator.next();
			Book book = bookServiceExternal.getBook(item.getBookId());
			
			
			if (book.getBookQuantity() < item.getQuantity()) {
				System.err.println(" Entered qunatity of " + book.getBookTitle() + " is greater than "
						+ "available please check and enter available quantity");
				iterator.remove();
			} else if (item.getQuantity() <= 0) {
				System.err.println(" You have entered invalid quantity for " + book.getBookTitle());
				iterator.remove();

			} else if (book.getBookQuantity() >= item.getQuantity())

			{

				item.setBookPrice(book.getBookPrice());
				totalprice += (item.getBookPrice() * item.getQuantity());

			}
		}
		c.setTotalPrice(totalprice);

		c.getItems().addAll(items);

		
		ApiResponse response = ApiResponse.builder().message("Books added to cart").success(true)
				.status(HttpStatus.ACCEPTED).result(cartRepository.save(c)).build();
		return new ResponseEntity <>(response, HttpStatus .ACCEPTED);
		

	}

	/*
	 *      old code
	 * public Cart addCart(Cart cart) { Integer totalprice = 0; try {
	 * 
	 * Cart c = getCartById(cart.getCustomerId());
	 * 
	 * 
	 * totalprice = c.getTotalPrice(); List<Items> items = cart.getItems();
	 * 
	 * Iterator<Items> iterator = items.iterator(); while (iterator.hasNext()) {
	 * 
	 * Items item = iterator.next(); Book book =
	 * bookServiceExternal.getBook(item.getBookId());
	 * 
	 * if (book.getBookQuantity() < item.getQuantity()) {
	 * System.err.println(" Entered qunatity of " + book.getBookTitle() +
	 * " is greater than " + "available please check and enter available quantity");
	 * iterator.remove(); } else if (item.getQuantity() <= 0) {
	 * System.err.println(" You have entered invalid quantity for " +
	 * book.getBookTitle()); iterator.remove();
	 * 
	 * } else if (book.getBookQuantity() >= item.getQuantity())
	 * 
	 * { item.setBookPrice(book.getBookPrice());
	 * 
	 * totalprice += (item.getBookPrice() * item.getQuantity());
	 * 
	 * } } c.setTotalPrice(totalprice);
	 * 
	 * c.getItems().addAll(items);
	 * 
	 * return cartRepository.save(c); }
	 * 
	 * catch (Exception ex) {
	 * 
	 * List<Items> items = cart.getItems(); Iterator<Items> iterator =
	 * items.iterator(); while (iterator.hasNext()) { Items item = iterator.next();
	 * 
	 * Book book = bookServiceExternal.getBook(item.getBookId());
	 * 
	 * if (book.getBookQuantity() < item.getQuantity()) {
	 * System.err.println(" Entered qunatity of " + book.getBookTitle() +
	 * " is greater than " + "available please check and enter available quantity");
	 * iterator.remove(); } else if (item.getQuantity() <= 0) {
	 * System.err.println(" You have entered invalid quantity for " +
	 * book.getBookTitle()); iterator.remove();
	 * 
	 * } else if (book.getBookQuantity() >= item.getQuantity())
	 * 
	 * { item.setBookPrice(book.getBookPrice()); totalprice += (item.getBookPrice()
	 * * item.getQuantity());
	 * 
	 * }
	 * 
	 * }
	 * 
	 * cart.setTotalPrice(totalprice); return cartRepository.save(cart); }
	 * 
	 * }
	 */

	public Cart deleteBook(String cid, Integer bid) {
		Cart c = getCartById(cid);
		Integer totalPrice = c.getTotalPrice();

		List<Items> items = c.getItems();
		Iterator<Items> iterator = items.iterator();
		while (iterator.hasNext()) {
			Items item = iterator.next();

			if (item.getBookId().equals(bid)) {
				totalPrice = totalPrice - (item.getBookPrice() * item.getQuantity());
				iterator.remove();
			}
		}
		c.setTotalPrice(totalPrice);
		cartRepository.save(c);
		return c;
	}

	public String deleteCart(String cid) {
		Cart c = getCartById(cid);
		cartRepository.delete(c);
		return "customer :" + cid + " cart is cleared";
	}

	public Cart getCartById(String cid) {
		return cartRepository.findByCustomerId(cid);
	}

	public List<Cart> getAllCart() {
		return cartRepository.findAll();
	}

}
