package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;

public class CategoryValidator extends Validator {
    public void validate(CategoryRequest request) {
        if (request.getName().isEmpty() || request.getName() == null)
            throw new BadRequestException("Category name must be not null");
    }

    public void validate(String id, CategoryRequest request) {
        validate(id);
        validate(request);
    }
}
