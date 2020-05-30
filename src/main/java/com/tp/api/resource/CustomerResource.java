package com.tp.api.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tp.api.model.Customer;
import com.tp.api.repository.CustomerRepository;

import io.swagger.annotations.ApiOperation;

import java.util.List;


@RestController
public class CustomerResource {

	@Autowired
    private CustomerRepository customerRepository;

	
	@ApiOperation(value = "Returns a list of all existing customers")
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    
	@ApiOperation(value = "Adds a new customer")
    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
    
    
	@ApiOperation(value = "Updates an existing customer infos")
    @PutMapping("/customers/{id}")
    public Customer replaceEmployee(@RequestBody Customer newCustomer, @PathVariable Integer id) {
      return customerRepository.findById(id)
        .map(customer -> {
          customer.setName(newCustomer.getName());
          customer.setEmail(newCustomer.getEmail());
          customer.setPhone(newCustomer.getPhone());
          customer.setCity(customer.getCity());
          return customerRepository.save(customer);
        })
        .orElseGet(() -> {
          newCustomer.setId(id);
          return customerRepository.save(newCustomer);
        });
    }

    
	@ApiOperation(value = "Deletes an existing customer")
    @DeleteMapping("/customers/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
        return "Deleted Successfully";
    }
    
    
    
}
