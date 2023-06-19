package com.orders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orders.entities.Orders;
import com.orders.repository.OrdersRepository;

@Service
public class OrderServices {
	
	@Autowired
	OrdersRepository ordersRepository;

	public Orders addOrder(Orders orders)
	{
		return ordersRepository.save(orders);
	}
	
	
	public Orders getOrderById(Integer orderId)
	{
		return ordersRepository.findById(orderId).get();
	}
	
	
	public List<Orders> getAllOrders()
	{
		return ordersRepository.findAll();
	}
	
	public List<Orders >getOrdersByCustomerId(String customerId)
	{
		return ordersRepository.findByCustomerId(customerId);
	}
	
}
