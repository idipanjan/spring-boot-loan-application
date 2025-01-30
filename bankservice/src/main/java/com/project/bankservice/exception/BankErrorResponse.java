package com.project.bankservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankErrorResponse {
    private int stausCode;
    private String error;
    private String message;
    private String timestamp;

}
