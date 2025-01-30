package com.project.loanservice.exception;

public class ClientException extends RuntimeException{
    public ClientException(String message){
        super(message);
    }
}