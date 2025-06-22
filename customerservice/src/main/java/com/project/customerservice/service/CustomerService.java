package com.project.customerservice.service;

import com.project.customerservice.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    Customer getCustomer(Long customerId);
    List<Customer> getCustomers();
    Long deleteCustomer(Long id);
}
