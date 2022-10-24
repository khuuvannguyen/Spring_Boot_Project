package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import com.vannguyen.SpringBootProject.application.validators.ProductValidator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    IProductService service;

    static ProductValidator validator = new ProductValidator();

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public List<ProductResponse> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public ProductResponse get(String id) {
        validator.validate(id);
        return service.get(UUID.fromString(id));
    }

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        validator.validate(request);
        return service.create(request);
    }

    @PutMapping(value = "/{id}")
    public ProductResponse update(String id, @RequestBody ProductRequest request) {
        validator.validate(id, request);
        return service.update(UUID.fromString(id), request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(String id) {
        validator.validate(id);
        service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
