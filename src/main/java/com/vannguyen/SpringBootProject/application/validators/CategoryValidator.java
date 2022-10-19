package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryValidator {
    public void validate(CategoryRequest request) {
        if (request.getName().isEmpty() || request.getName() == null)
            throw new BadRequestException("Category name must be not null");
    }

    public void validate(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if(!id.matches(regex))
            throw new BadRequestException("Id is not correct");
        if (id.isEmpty() || id == null)
            throw new BadRequestException("Id must be not null");
    }

    public void validate(String id, CategoryRequest request) {
        validate(id);
        validate(request);
    }
}
