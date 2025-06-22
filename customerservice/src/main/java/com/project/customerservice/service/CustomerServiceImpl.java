package com.project.customerservice.service;

import com.project.customerservice.Exception.ClientException;
import com.project.customerservice.Exception.ResourceNotFoundException;
import com.project.customerservice.Exception.ResoureExistsException;
import com.project.customerservice.client.BankServiceFeignClient;
import com.project.customerservice.entity.Bank;
import com.project.customerservice.entity.Customer;
import com.project.customerservice.repository.CustomerRepository;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    BankServiceFeignClient bankServiceFeignClient;

    // Retry and CircuitBreaker annotations with fallback method
    @Retry(name="BANK-MICROSERVICE", fallbackMethod = "fallbackSaveCustomer")
    public Customer saveCustomer(Customer customer) {

//         final String URL="http://BANK-MICROSERVICE/api/bank/";
//            ResponseEntity<Bank> response = restTemplate.exchange(
//                    URL + customer.getBankId(),
//                    HttpMethod.GET,
//                    HttpEntity.EMPTY,
//                    Bank.class
//            );
            boolean existingCustomer = checkExistingCustomerByEmail(customer.getEmail());
            if(existingCustomer){
                throw new ResoureExistsException("Customer already exists");
            }
            ResponseEntity<Bank> response = bankServiceFeignClient.findBankById(customer.getBankId());

            // If the bank is found, save the customer
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                //set random account number
                String accountNo = getAccountNumber(response.getBody().getName());
                customer.setAccountNumber(accountNo);
                return customerRepository.save(customer);
            }
            return null;
    }

    public Customer getCustomer(Long customerId) {
    return customerRepository
        .findById(customerId)
        .orElseThrow(() -> new ResourceNotFoundException("No Customer found with id "+ customerId));
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Long deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Customer found with id "+ id));
        customerRepository.delete(customer);
        return id;
    }

    public boolean checkExistingCustomerByEmail(String email) {
        if(customerRepository.findByEmail(email) == null){
            return false;
        }
        return true;
    }
    // Fallback method when retries or circuit breaker is triggered
    public Customer fallbackSaveCustomer(Customer customer, Throwable throwable) {
        throw new ClientException(throwable.getMessage());
    }


    public static String getAccountNumber(String bankName) {
        // Get the first 2 characters of the bank name
        String bankCode = bankName.substring(0, 2).toUpperCase();

        // Generate a random 10-digit number (ensuring the first digit is not zero)
        Random random = new Random();
        StringBuilder randomDigits = new StringBuilder();

        // Ensure the first digit is not 0
        randomDigits.append(random.nextInt(9) + 1);

        // Generate the remaining digits
        for (int i = 1; i < 10; i++) {
            randomDigits.append(random.nextInt(10));
        }

        // Return the final account number
        return bankCode + randomDigits.toString();
    }
}