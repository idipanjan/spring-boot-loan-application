package com.project.bankservice.service;

import com.project.bankservice.model.Bank;

import java.util.List;

public interface BankService {
    Bank saveBank(Bank bank);
    Bank getBankById(Long bankId);
    List<Bank> getAllBanks();
    Long deleteBank(Long id);
}
