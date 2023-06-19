package com.books.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.books.entities.Books;
import com.books.exceptions.BookNotFoundException;
import com.books.exceptions.InvalidQuantityException;
import com.books.repository.BooksRepository;
import com.books.payload.ApiResponse;

@Service
public class BookService {

	@Autowired
	private BooksRepository booksRepository;

	public ResponseEntity<ApiResponse> addBooks(Books book) {
		Books b = booksRepository.save(book);

		ApiResponse response = ApiResponse.builder().message("Book added Successfully").success(true)
				.status(HttpStatus.ACCEPTED).result(b).build();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	
	}

	public Books getBookById(Integer bookId) {
		
		Optional<Books> book = booksRepository.findById(bookId);
		if(book.isEmpty())
			throw new BookNotFoundException("No book found with id: " + bookId);
		else
		{
			return book.get();
		}
			
	}

	public ResponseEntity<ApiResponse> getAllBooks() {
		List<Books> books = booksRepository.findAll();
		if (books.isEmpty())
			throw new BookNotFoundException();
		else
		{
			ApiResponse response = ApiResponse.builder().message("All Books retrieved").success(true)
					.status(HttpStatus.ACCEPTED).result(books).build();
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}
			
	}

	public Books orderBook(Integer bookid, Integer qty) {
		
		Optional<Books> b = booksRepository.findById(bookid);
		if(b.isEmpty())
			throw new BookNotFoundException("No book found with id: " + bookid);
		else {
			Books book = b.get();
		if (qty <= 0)
			throw new InvalidQuantityException();

		if (book.getBookQuantity() >= qty) {
			book.setBookQuantity(book.getBookQuantity() - qty);
			book.setBookLastPurchase( new java.sql.Date(System.currentTimeMillis()));
			Integer totalSales =  book.getBookTotalSales();
			book.setBookTotalSales(qty + totalSales);
			System.err.println(book.getBookTotalSales());
			System.err.println(book.getBookLastPurchase());
			booksRepository.save(book);
			
			return book;
		} else
			throw new InvalidQuantityException(
					"the Available books are " + book.getBookQuantity() + " and ordered quantity is " + qty);

		}
	}

	public ResponseEntity<ApiResponse> updateBooksQty(Integer bookid, Integer qty) {
		if (qty < 0)
			throw new InvalidQuantityException("the quantity of books cannot be less than zero");

		Optional<Books> book = booksRepository.findById(bookid);
		if(book.isEmpty())
			throw new BookNotFoundException("No book found with id: " + bookid);
		else
		{
			Books b = book.get();
		b.setBookQuantity(qty);		
		booksRepository.save(b);
		ApiResponse response = ApiResponse.builder().message("Book quantity updated1").success(true)
				.status(HttpStatus.ACCEPTED).result(b).build();
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	
		}
	}

	public ResponseEntity<ApiResponse> removeBook(Integer bookId) {
		{
			Optional<Books> b = booksRepository.findById(bookId);
			if(b.isEmpty())
				throw new BookNotFoundException("No book found with id: " + bookId);
			
			booksRepository.deleteById(bookId);
			
			ApiResponse response = ApiResponse.builder().message("Book with ID: " + bookId + " is deleted").success(true)
					.status(HttpStatus.ACCEPTED).result(b.get()).build();
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			
		}

	}

}
