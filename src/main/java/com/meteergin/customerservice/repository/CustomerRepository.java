/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteergin.customerservice.repository;

import com.meteergin.customerservice.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mergin
 */
public interface CustomerRepository extends JpaRepository<Customer, String>{
    
    public Optional<Customer> findByEmail(String email);
    
}
