package com.project.bankservice.service;

import com.project.bankservice.exception.ResourceNotFoundException;
import com.project.bankservice.entity.Bank;
import com.project.bankservice.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    BankRepository bankRepository;

    @Override
    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public Bank getBankById(Long id) {
        return bankRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Bank not found with id " + id));
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Long deleteBank(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bank not found with id: "+id));
        bankRepository.delete(bank);
        return bank.getId();
    }
}
