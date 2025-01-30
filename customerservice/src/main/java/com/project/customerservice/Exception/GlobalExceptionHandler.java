package com.project.customerservice.Exception;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiErrorResponse errorResponse  = new ApiErrorResponse();
        errorResponse.setError(ex.getMessage());
        errorResponse.setMessage("Not found");
        errorResponse.setStausCode(404);
        errorResponse.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errorDetails = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errorDetails.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResoureExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleResoureExistsException(ResoureExistsException ex){
        ApiErrorResponse errorResponse  = new ApiErrorResponse();
        errorResponse.setError(ex.getMessage());
        errorResponse.setMessage("Resource already exists");
        errorResponse.setStausCode(400);
        errorResponse.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<ApiErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
