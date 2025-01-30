package com.project.customerservice.client;

import com.project.customerservice.model.Bank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="BANK-MICROSERVICE", url = "http://localhost:8080")
public interface BankServiceFeignClient {

    @GetMapping("/api/banks/{id}")
    ResponseEntity<Bank> findBankById(@PathVariable Long id);

}
