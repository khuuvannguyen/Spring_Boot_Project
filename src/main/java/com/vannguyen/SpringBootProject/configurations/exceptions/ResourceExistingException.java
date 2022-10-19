package com.vannguyen.SpringBootProject.configurations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceExistingException extends RuntimeException {
    public ResourceExistingException(String message) {
        super(message);
    }

    public ResourceExistingException(){
        super();
    }
}
