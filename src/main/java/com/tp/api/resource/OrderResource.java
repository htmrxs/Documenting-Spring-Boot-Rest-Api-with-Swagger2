package com.tp.api.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tp.api.model.Order;
import com.tp.api.repository.CustomerRepository;
import com.tp.api.repository.OrderRepository;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import com.tp.api.exception.ResourceNotFoundException;

@RestController
public class OrderResource {

	
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    
    @ApiOperation(value = "Returns a list of all existing orders for a given customer")
    @GetMapping("/customers/{id}/orders")
    public List<Order> getAllOrdersByCustomerId(@PathVariable (value = "id") Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    
    @ApiOperation(value = "Adds a new order for a given customer")
    @PostMapping("/customers/{id}/orders")
    public Order createOrder(@PathVariable (value = "id") Integer id,
                                   @RequestBody Order order) {
        return customerRepository.findById(id).map(customer -> {
            order.setCustomer(customer);
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("CustomerId " + id + " not found"));
    }
    
    
    @ApiOperation(value = "Updates an existing order for a given customer")
    @PutMapping("/customers/{customerId}/orders/{orderId}")
    public Order replaceOrder(@PathVariable (value = "customerId") Integer customerId,
                                 @PathVariable (value = "orderId") Integer orderId,
                                 @RequestBody Order newOrder) {
        return orderRepository.findById(orderId).map(order -> {
        	order.setProductName(newOrder.getProductName());
            order.setOrderDate(newOrder.getOrderDate());
            order.setQuantity(newOrder.getQuantity());
            order.setShippingDestination(newOrder.getShippingDestination());
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + orderId + "not found")); 	//
    }
    
    
    @ApiOperation(value = "Deletes an existing order for a given customer")
    @DeleteMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "customerId") Integer customerId,
                              @PathVariable (value = "orderId") Integer orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + orderId + " and customerId " + customerId));   
    }
    
    
    
}
