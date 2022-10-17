package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.domain.services.interfaces.ICategoryService;
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

    @GetMapping
    public List<CategoryResponse> get() {
        return service.get();
    }

    @GetMapping(params = "id")
    public CategoryResponse get(String id) {
        return service.get(UUID.fromString(id));
    }

    @PostMapping
    public CategoryResponse create(@RequestBody CategoryRequest request) {
        return service.create(request);
    }

    @PutMapping(params = "id")
    public CategoryResponse update(String id, @RequestBody CategoryRequest request) {
        return service.update(UUID.fromString(id), request);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity delete(String id) {
        service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
