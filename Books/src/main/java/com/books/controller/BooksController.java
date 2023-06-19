package com.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.books.entities.Books;
import com.books.service.BookService;
import com.books.payload.ApiResponse;

@RestController
@RequestMapping("/book-service")
public class BooksController {

	@Autowired
	private BookService bookService;

	@PostMapping("add-books")
	public ResponseEntity<ApiResponse> addBooks(@RequestBody Books book) {
		return bookService.addBooks(book);

	}

	@GetMapping("/get-book/{bookId}")
	public Books getBookById(@PathVariable Integer bookId) {
		return bookService.getBookById(bookId);

	}

	@GetMapping("get-all-books")
	public ResponseEntity<ApiResponse> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/order-book/{bookid}/{qty}")
	public Books orderBook(@PathVariable Integer bookid, @PathVariable Integer qty) {
		return bookService.orderBook(bookid, qty);
	}

	@GetMapping("/update-book/{bookid}/{qty}")
	public ResponseEntity<ApiResponse> updateBooksQty(@PathVariable Integer bookid, @PathVariable Integer qty) {
		return bookService.updateBooksQty(bookid, qty);
	}

	@GetMapping("/remove-book/{bookId}")
	public ResponseEntity<ApiResponse> removeBook(@PathVariable Integer bookId) {
		return bookService.removeBook(bookId);
	}

}
