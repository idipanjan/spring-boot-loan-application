package com.project.loanservice.service;

import com.project.loanservice.client.BankServiceFeignClient;
import com.project.loanservice.client.CustomerServiceFeignClient;
import com.project.loanservice.exception.ClientException;
import com.project.loanservice.model.Bank;
import com.project.loanservice.model.Customer;
import com.project.loanservice.model.Loan;
import com.project.loanservice.repository.LoanRepository;
import feign.FeignException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class LoanServiceImpl implements LoanService{
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BankServiceFeignClient bankServiceFeignClient;

    @Autowired
    CustomerServiceFeignClient customerServiceFeignClient;

    @Override
    public Loan addLoan(Loan loan) {
        // Retryable Bank service call
        ResponseEntity<Bank> bankServiceResponse = callBankService(loan.getBankId());

        // Retryable Customer service call
        ResponseEntity<Customer> customerServiceResponse = callCustomerService(loan.getCustomerId());

        if (bankServiceResponse.getStatusCode().is2xxSuccessful() &&
                customerServiceResponse.getStatusCode().is2xxSuccessful() &&
                bankServiceResponse.getBody() != null &&
                customerServiceResponse.getBody() != null) {
            return loanRepository.save(loan);
        } else {
            System.out.println("------- Something went wrong! -------");
        }

        return null;
    }

    // Retryable method for calling the Bank service
    @Retry(name = "BANK-MICROSERVICE", fallbackMethod = "fallbackCheckBank")
    public ResponseEntity<Bank> callBankService(Long bankId) {
        try {
            return bankServiceFeignClient.findBankById(bankId);
        } catch (FeignException.NotFound e) {
            throw new ClientException("Bank not found: " + e.getMessage());
        } catch (FeignException e) {
            throw new ClientException("Feign exception occurred while calling bank service: " + e.getMessage());
        }
    }

    // Retryable method for calling the Customer service
    @Retry(name = "CUSTOMER-MICROSERVICE", fallbackMethod = "fallbackCheckCustomer")
    public ResponseEntity<Customer> callCustomerService(Long customerId) {
        try {
            return customerServiceFeignClient.findCusomerById(customerId);
        } catch (FeignException.NotFound e) {
            throw new ClientException("Customer not found: " + e.getMessage());
        } catch (FeignException e) {
            throw new ClientException("Feign exception occurred while calling customer service: " + e.getMessage());
        }
    }

    // Fallback method for Customer service
    public void fallbackCheckCustomer(Long customerId, Throwable throwable) {
        System.out.println("Customer service problem: " + throwable.getMessage());
        throw new ClientException("Fallback triggered for Customer service: " + throwable.getMessage());
    }

    // Fallback method for Bank service
    public void fallbackCheckBank(Long bankId, Throwable throwable) {
        System.out.println("Bank service problem: " + throwable.getMessage());
        throw new ClientException("Fallback triggered for Bank service: " + throwable.getMessage());
    }
}
