package com.orderzzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderzzz.entities.Orderzzz;

public interface OrderzzzRepository extends JpaRepository<Orderzzz, String> {

	
	List<Orderzzz > findByCustomerId(String cid);
}
