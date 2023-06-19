package com.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orders.entities.Orders;
import com.orders.services.OrderServices;

@RestController
@RequestMapping("/orders-service")
public class OrdersController {

	@Autowired
	OrderServices orderServices;

	@PostMapping("/addOrder")
	public Orders addOrder(@RequestBody Orders orders) {
		// System.err.println(orders);
		return orderServices.addOrder(orders);
	}

	@GetMapping("/getOrderById/{orderId}")
	public Orders getOrderById(@PathVariable Integer orderId) {
		return orderServices.getOrderById(orderId);
	}

	@GetMapping("/getAllOrders")
	public List<Orders> getAllOrders() {
		return orderServices.getAllOrders();
	}

	@GetMapping("/customerId")
	public List<Orders> getOrdersByCustomerId(@RequestHeader("userInfo") String customerId) {
		return orderServices.getOrdersByCustomerId(customerId);
	}

}
