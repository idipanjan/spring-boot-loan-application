package com.project.bankservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "bank name is mandatory")
    @NotBlank(message = "bank name cannot be blank")
    private String name;

    @NotNull(message = "bank branch is mandatory")
    @NotBlank(message = "bank branch cannot be blank")
    private String branch;

    @NotNull(message = "bank address is mandatory")
    @NotBlank(message = "bank address cannot be blank")
    private String address;
}
