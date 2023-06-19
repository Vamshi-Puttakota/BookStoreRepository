package com.cart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

@Table(name = "trying_items")
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer itemId;
	private Integer bookId;
	private Integer quantity;
	private Integer bookPrice;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	private Cart cart;
}
