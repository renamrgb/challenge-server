package com.github.renamrgb.challengeserver.application.services.exceptions;

public class ResourceNotfoundException extends RuntimeException{
    public ResourceNotfoundException(String message) {
        super(message);
    }
}
