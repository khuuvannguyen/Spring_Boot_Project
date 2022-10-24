package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.application.validators.CategoryValidator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    ICategoryService service;

    static CategoryValidator validator = new CategoryValidator();

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public List<CategoryResponse> get() {
        return service.get();
    }

    @GetMapping(value = "/{id}")
    public CategoryResponse get(String id) {
        validator.validate(id);
        return service.get(UUID.fromString(id));
    }

    @PostMapping
    public CategoryResponse create(@RequestBody CategoryRequest request) {
        validator.validate(request);
        return service.create(request);
    }

    @PutMapping(value = "/{id}")
    public CategoryResponse update(String id, @RequestBody CategoryRequest request) {
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
