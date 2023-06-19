package com.cart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer itemId;
	@Column(columnDefinition = "integer default 0")
	private Integer bookId;
	
	@Column(columnDefinition = "integer default 0")
	private Integer quantity;
	@Column(columnDefinition = "integer default 0")
	private Integer bookPrice;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonBackReference
	private Cart cart;
}
