package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cart.entities.Items;
import java.util.List;
import java.util.Optional;

import com.cart.entities.Cart;



public interface ItemsRepository extends JpaRepository<Items, Integer> {

	@Query (value = "SELECT * FROM ITEMS WHERE book_id = :bookId AND customer_id = :customerId", nativeQuery = true)
	Optional<Items> findByBookIdAndCustomer(@Param("bookId") Integer bookId, @Param("customerId")String customerId);
	
}
