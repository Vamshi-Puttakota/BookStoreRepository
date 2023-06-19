package com.orderzzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderzzz.entities.OrderzzzItems;
import java.util.List;


public interface OrderzzzItemsRepository extends JpaRepository<OrderzzzItems, Integer> {

	
}
