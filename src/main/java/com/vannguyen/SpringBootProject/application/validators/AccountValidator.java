package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;

public class AccountValidator extends Validator {
    public void validate(AccountRequest request) {
        if (request.getUsername().trim().isEmpty())
            throw new BadRequestException("Username must be not null");
        if (request.getPassword().trim().isEmpty())
            throw new BadRequestException("Password must be not null");
        if (request.getRoles().trim().isEmpty())
            throw new BadRequestException("Roles must be not null");
    }

    public void validate(String id, AccountRequest request) {
        validate(request);
        validate(id);
    }
}
