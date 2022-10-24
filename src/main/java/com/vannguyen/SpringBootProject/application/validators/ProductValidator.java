package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;

public class ProductValidator extends Validator {
    private boolean isUpdate = false;

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
        if (request.getPrice() < 0L)
            throw new BadRequestException("Product price must be positive");
    }

    public void validate(String id, ProductRequest request) {
        isUpdate = true;
        validate(id);
        validate(request);
    }
}
