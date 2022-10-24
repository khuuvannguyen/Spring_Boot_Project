package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.OrderRequest;
import com.vannguyen.SpringBootProject.application.responses.OrderResponse;
import com.vannguyen.SpringBootProject.application.validators.OrderValidator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    IOrderService _service;

    static OrderValidator validator = new OrderValidator();

    static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @GetMapping
    public List<OrderResponse> get() {
        return _service.get();
    }

    @GetMapping(value = "/{id}")
    public OrderResponse get(String id) {
        validator.validate(id);
        return _service.get(UUID.fromString(id));
    }

    @PostMapping
    public OrderResponse create(@RequestBody OrderRequest request) {
        validator.validate(request);
        return _service.create(request);
    }

    @PutMapping(value = "/{orderId}")
    public OrderResponse update(String orderId, @RequestBody OrderRequest request) {
        validator.validate(orderId, request);
        return _service.update(UUID.fromString(orderId), request);
    }

    @PutMapping(value = "/{orderId}/{detailId}")
    public void update(String orderId, String detailId, @RequestBody OrderRequest request) {
        validator.validate(orderId, detailId, request);
        _service.update(UUID.fromString(orderId), UUID.fromString(detailId), request);
    }
}
