package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;

public class Validator {
    public void validate(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if(!id.matches(regex))
            throw new BadRequestException("Id is not correct");
        if (id.isEmpty() || id == null)
            throw new BadRequestException("Id must be not null");
    }
}
