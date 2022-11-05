package com.github.renamrgb.challengeserver.application.controllers.exceptions;

import com.github.renamrgb.challengeserver.application.services.exceptions.ResourceNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.github.renamrgb.challengeserver.domain.StandardMessages.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardErrorMessage IllegalArgumentException (IllegalArgumentException e) {
        StandardErrorMessage err = new StandardErrorMessage();
        err.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        err.setMessage(ILLEGAL_ARGUMENT);
        return err;
    }

    @ExceptionHandler(ResourceNotfoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StandardErrorMessage resourceNotFound(ResourceNotfoundException e) {
        StandardErrorMessage err = new StandardErrorMessage();
        err.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        err.setMessage(e.getMessage());
        return err;
    }
}
