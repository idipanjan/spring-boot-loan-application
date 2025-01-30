package com.project.loanservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "customerId is mandatory")
    private Long customerId;

    @NotNull(message = "bankId is mandatory")
    private Long bankId;

    @NotNull(message = "loan amount is mandatory")
    @DecimalMin(value = "10000.0", message = "Loan amount must be greater than or equal to 10000")
    @DecimalMax(value = "10000000.0", message = "Loan amount must be less than or equal to 10000000")
    private Double loanAmount;



}
