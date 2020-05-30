package com.tp.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	List<Order> findByCustomerId(Integer customerId);
	Optional<Order> findByIdAndCustomerId(Integer id, Integer customerId);
}
