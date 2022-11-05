package com.github.renamrgb.challengeserver.application.controllers.exceptions;

import java.io.Serializable;

public class StandardErrorMessage implements Serializable {

    private String error;
    private String message;

    public StandardErrorMessage() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
