package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.CategoryRequest;
import com.vannguyen.SpringBootProject.application.responses.CategoryResponse;
import com.vannguyen.SpringBootProject.application.validators.CategoryValidator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Get all Category", description = "Return all Categories in database", tags = "Category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            })
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public List<CategoryResponse> get() {
        return service.get();
    }

    @Operation(summary = "Get Category by id", description = "Return a single Category", tags = "Category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            })
    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public CategoryResponse get(String id) {
        validator.validate(id);
        return service.get(UUID.fromString(id));
    }

    @Operation(summary = "Add new Category", description = "", tags = "Category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            })
    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        validator.validate(request);
        return new ResponseEntity<>(service.create(request),HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing Category", description = "", tags = "Category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            })
    @PutMapping(value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public CategoryResponse update(String id, @RequestBody CategoryRequest request) {
        validator.validate(id, request);
        return service.update(UUID.fromString(id), request);
    }

    @Operation(summary = "Delete an existing Category", tags = "Category")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @DeleteMapping(value = "/{id}",
    produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity delete(String id) {
        validator.validate(id);
        service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
