package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductValidator {
    private boolean isUpdate = false;

    public void validate(String id) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        if(!id.matches(regex))
            throw new BadRequestException("Id is not correct");
        if (id.isEmpty() || id == null)
            throw new BadRequestException("Id must be not null");
    }

    public void validate(ProductRequest request) {
        if (request.getName().isEmpty() || request.getName() == null)
            throw new BadRequestException("Product name must be not null");
        if (request.getCategory() == null)
            throw new BadRequestException("Category must be not null");
        if (request.getCreatedBy() == null)
            throw new BadRequestException("Created account must be not null");
        if (!isUpdate) {
            if (request.getUpdatedBy() != null)
                throw new BadRequestException("Updated account must be null");
        } else {
            if (request.getUpdatedBy() == null)
                throw new BadRequestException("Updated account must not be null");
        }
    }

    public void validate(String id, ProductRequest request) {
        isUpdate = true;
        validate(id);
        validate(request);
    }
}
