package com.project.bankservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BankErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        BankErrorResponse errorResponse = new BankErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setStausCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<BankErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorDetails = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errorDetails.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
