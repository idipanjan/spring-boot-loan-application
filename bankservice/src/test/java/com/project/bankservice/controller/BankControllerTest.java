package com.project.bankservice.controller;

import com.project.bankservice.entity.Bank;
import com.project.bankservice.service.BankService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BankControllerTest {

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    private MockMvc mockMVC;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMVC = MockMvcBuilders.standaloneSetup(bankController).build();
    }

    @Test
    void addBank() throws Exception {
        Bank bank = new Bank("Axis Bank","mumbai","Mumbai,India");
        Mockito.when(bankService.saveBank(bank)).thenReturn(bank);

        mockMVC.perform(post("/api/banks/")
                        .contentType("application/json")
                        .content("{\"name\":\"Axis Bank\",\"branch\":\"mumbai\",\"address\":\"Mumbai,India\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Axis Bank"))
                .andExpect(jsonPath("$.branch").value("mumbai"))
                .andExpect(jsonPath("$.address").value("Mumbai,India"));
    }

    @Test
    void fetchAllBanks() throws Exception {
        List<Bank> banks = List.of(
                new Bank("Axis Bank", "mumbai", "Mumbai, India"),
                new Bank("HDFC Bank", "delhi", "Delhi, India")
        );

        Mockito.when(bankService.getAllBanks()).thenReturn(banks);
        mockMVC.perform(get("/api/banks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Axis Bank"))
                .andExpect(jsonPath("$[1].name").value("HDFC Bank"));
    }

    @Test
    void fetchBank() throws Exception {
        Bank bank = new Bank("Axis Bank", "mumbai", "Mumbai, India");
        Mockito.when(bankService.getBankById(1L)).thenReturn(bank);

        mockMVC.perform(get("/api/banks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Axis Bank"))
                .andExpect(jsonPath("$.branch").value("mumbai"))
                .andExpect(jsonPath("$.address").value("Mumbai, India"));

    }

    @Test
    void deleteBank() throws Exception{
        Long bankId = 1L;
        Mockito.when(bankService.deleteBank(bankId)).thenReturn(bankId);

        mockMVC.perform(delete("/api/banks/" + bankId))
                .andExpect(status().isOk())
                .andExpect(content().string("Bank deleted with id: " + bankId));


    }
}