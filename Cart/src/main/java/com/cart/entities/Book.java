package com.cart.entities;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

	@Id
	private Integer bookId;
	private String bookTitle;
	private String bookAuthor;
	private Integer bookPrice;
	private Integer bookQuantity;
	private String bookLastPurchase;
	private Integer bookTotalSales;
}
