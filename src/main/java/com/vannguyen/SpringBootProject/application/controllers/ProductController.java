package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.ProductRequest;
import com.vannguyen.SpringBootProject.application.responses.ProductResponse;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IProductService;
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

    @GetMapping
    public List<ProductResponse> get() {
        return service.get();
    }

    @GetMapping(params = "id")
    public ProductResponse get(String id) {
        return service.get(UUID.fromString(id));
    }

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        try {
            return service.create(request);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @PutMapping(params = "id")
    public ProductResponse update(String id, @RequestBody ProductRequest request) {
        return service.update(UUID.fromString(id), request);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity delete(String id) {
        service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
