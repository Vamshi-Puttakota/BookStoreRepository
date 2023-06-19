package com.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orders.entities.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{
	
	List<Orders> findByCustomerId(String customerId);

	
}
