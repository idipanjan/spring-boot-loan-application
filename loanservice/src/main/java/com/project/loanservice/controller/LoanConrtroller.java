package com.project.loanservice.controller;

import com.project.loanservice.model.Loan;
import com.project.loanservice.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanConrtroller {

    @Autowired
    LoanService loanService;

    @GetMapping("/check")
    public String check(){
        return " loan service up...";
    }

    @PostMapping("/")
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody Loan loan){
        Loan createdLoan = loanService.addLoan(loan);
        return new ResponseEntity<>(createdLoan, HttpStatus.OK);
    }
}
