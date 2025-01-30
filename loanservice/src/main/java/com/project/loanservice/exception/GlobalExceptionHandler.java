package com.project.loanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorDetails = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errorDetails.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiErrorResponse> handleClientException(ClientException ex){
        ApiErrorResponse errorResponse  = new ApiErrorResponse();
        errorResponse.setError(ex.getMessage());
        errorResponse.setMessage("Client side exception or server is down");
        errorResponse.setStausCode(400);
        errorResponse.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
