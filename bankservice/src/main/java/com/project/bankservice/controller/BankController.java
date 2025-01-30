package com.project.bankservice.controller;

import com.project.bankservice.model.Bank;
import com.project.bankservice.service.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    @Autowired
    BankService bankService;

    @PostMapping("/")
    public ResponseEntity<Bank> addBank(@Valid @RequestBody Bank bank){
        Bank newBank = bankService.saveBank(bank);
        return new ResponseEntity<Bank>(bank, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Bank>> fetchAllBanks(){
        List<Bank> banks = bankService.getAllBanks();
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> fetchBank(@PathVariable Long id){
        Bank bank = bankService.getBankById(id);
        return new ResponseEntity<>(bank, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBank(@PathVariable Long id){
        return new ResponseEntity<>("Bank deleted with id: " + bankService.deleteBank(id), HttpStatus.OK);
    }

}
