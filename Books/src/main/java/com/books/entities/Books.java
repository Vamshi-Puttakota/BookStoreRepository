package com.books.entities;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books {

	@Id
	private Integer bookId;
	private String bookTitle;
	private String bookAuthor;
	private Integer bookPrice;
	private Integer bookQuantity;
	private Date bookLastPurchase;
	@Column(columnDefinition = "integer default 0")
	private Integer bookTotalSales;
}
