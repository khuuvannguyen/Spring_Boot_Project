package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountValidator {
    public void validate(AccountRequest request) {
        if (request.getUsername().trim().isEmpty())
            throw new BadRequestException("Username must be not null");
        if (request.getPassword().trim().isEmpty())
            throw new BadRequestException("Password must be not null");
        if (request.getRoles().trim().isEmpty())
            throw new BadRequestException("Roles must be not null");
    }

    public void validate(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if(!id.matches(regex))
            throw new BadRequestException("Id is not correct");
        if (id.trim().isEmpty() || id == null)
            throw new BadRequestException("Id must be not null");
    }

    public void validate(String id, AccountRequest request) {
        validate(request);
        validate(id);
    }
}
