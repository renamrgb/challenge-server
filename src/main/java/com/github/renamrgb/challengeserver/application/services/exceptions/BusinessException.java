package com.github.renamrgb.challengeserver.application.services.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}