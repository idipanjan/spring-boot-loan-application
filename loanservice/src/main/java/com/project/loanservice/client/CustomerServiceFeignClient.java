package com.project.loanservice.client;


import com.project.loanservice.entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CUSTOMER-MICROSERVICE", url = "http://localhost:8081")
public interface CustomerServiceFeignClient {

    @GetMapping("/api/customers/{id}")
    ResponseEntity<Customer> findCusomerById(@PathVariable Long id);
}
