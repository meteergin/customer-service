/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteergin.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meteergin.customerservice.entity.Customer;
import com.meteergin.customerservice.service.CustomerService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mergin
 */
@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer customer) {
        try {
            if (customer.getEmail().isBlank()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                Optional<Customer> c = customerService.findCustomerByEmail(customer.getEmail());
                if (c.isPresent()) {
                    return new ResponseEntity<>(new ObjectMapper().writeValueAsString(c.get()), HttpStatus.OK);
                } else {
                    customer.setId(UUID.randomUUID());
                    Customer cu = customerService.saveCustomer(customer);
                    return new ResponseEntity<>(new ObjectMapper().writeValueAsString(cu), HttpStatus.OK);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        try {
            Optional<Customer> c = customerService.findCustomerByEmail(email);
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(c), HttpStatus.OK);
        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRandomCustomer")
    public ResponseEntity<?> getRandomCustomer() {
        try {
            List<Customer> allCustomers = customerService.findAllCustomers();
            if (!allCustomers.isEmpty()) {
                Collections.shuffle(allCustomers);
                return new ResponseEntity<>(new ObjectMapper().writeValueAsString(allCustomers.get(0)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ObjectMapper().writeValueAsString(null), HttpStatus.OK);
            }
        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
