package com.vannguyen.SpringBootProject.application.validators;

import com.vannguyen.SpringBootProject.application.requests.OrderDetailRequest;
import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.configurations.exceptions.BadRequestException;

import java.util.List;

public class OrderValidator extends Validator {
    public void validate(OrderRequest request) {
        if (request.getOrderDetails().isEmpty())
            throw new BadRequestException("Order details must be not null and empty");
        for (OrderDetailRequest detail : request.getOrderDetails()) {
            validate(detail.getProduct().toString());
            if (detail.getQuantity() <= 0)
                throw new BadRequestException("Quantity must be greater than 0");
            if (detail.getQuantity() != (int) detail.getQuantity()) {
                throw new BadRequestException("Quantity must be decimal");
            }
        }
    }

    public void validate(String id, OrderRequest request) {
        validate(id);
        validate(request);
    }

    public void validate(String id1, String id2, OrderRequest request) {
        validate(id1);
        validate(id2);
        validate(request);
        if (request.getOrderDetails().size() > 1)
            throw new BadRequestException("This endpoint is using for update one Product in Order");
    }
}
