package com.project.bankservice.service;

import com.project.bankservice.entity.Bank;
import com.project.bankservice.exception.ResourceNotFoundException;
import com.project.bankservice.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBank() {
        Bank bank = new Bank("Axis Bank", "mumbai", "Mumbai, India");
        Mockito.when(bankRepository.save(bank)).thenReturn(bank);

        Bank savedBank = bankService.saveBank(bank);
        assertEquals(bank, savedBank);
        Mockito.verify(bankRepository).save(bank);
    }

    @Test
    void getBankById_found() {
        Long bankId = 1L;
        Bank bank = new Bank("Axis Bank", "mumbai", "Mumbai, India");
        bank.setId(bankId);
        Mockito.when(bankRepository.findById(bankId)).thenReturn(Optional.of(bank));

        Bank foundBank = bankService.getBankById(bankId);
        assertEquals(bank,foundBank);
        Mockito.verify(bankRepository).findById(bankId);
    }

    @Test
    void getBankById_not_found() {
        Long bankId = 1L;
        Mockito.when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bankService.getBankById(bankId)
        );
        assertEquals("Bank not found with id " + bankId, exception.getMessage());
        Mockito.verify(bankRepository).findById(bankId);
    }

    @Test
    void getAllBanks() {
        List<Bank> banks = List.of(
                new Bank("Axis Bank", "mumbai", "Mumbai, India"),
                new Bank("HDFC Bank", "delhi", "Delhi, India")
        );
        Mockito.when(bankRepository.findAll()).thenReturn(banks);
        List<Bank> allBanks = bankService.getAllBanks();
        assertEquals(banks, allBanks);
        Mockito.verify(bankRepository).findAll();
    }

    @Test
    void deleteBank_Id_found() {
        Long bankId = 1L;
        Bank bank = new Bank("Axis Bank", "mumbai", "Mumbai, India");
        bank.setId(bankId);
        Mockito.when(bankRepository.findById(bankId)).thenReturn(Optional.of(bank));

        Long deletedBankId = bankService.deleteBank(bankId);
        assertEquals(bankId, deletedBankId);
        Mockito.verify(bankRepository).findById(bankId);
        Mockito.verify(bankRepository).delete(bank);
    }
    @Test
    void deleteBank_Id_not_found() {
        Long bankId = 1L;
        Mockito.when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> bankService.deleteBank(bankId)
        );
        assertEquals("Bank not found with id: " + bankId, exception.getMessage());
        Mockito.verify(bankRepository).findById(bankId);
        Mockito.verify(bankRepository, Mockito.never()).delete(Mockito.any());
    }

}