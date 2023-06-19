package com.orderzzz.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Orderzzz {

	@Id
	private String orderzzzId;
	private String customerId;
	private Integer totalBill;
	
    @OneToMany(mappedBy = "orderzzz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
	private List<OrderzzzItems> orderzzzItems = new ArrayList<OrderzzzItems>();
	 
}
