package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.application.validators.AccountValidator;
import com.vannguyen.SpringBootProject.application.validators.Validator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    IAccountService _service;

    static AccountValidator validator = new AccountValidator();

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Operation(summary = "Get all Account in database", tags = "Account")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            })
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AccountResponse> get() {
        return _service.get();
    }

    @Operation(summary = "Get a single Account by username", tags = "Account")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            })
    @GetMapping(value = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AccountResponse get(String username) {
        return _service.get(username);
    }

    @Operation(summary = "Create a new Account", tags = "Account")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
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
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest request) {
        validator.validate(request);
        return new ResponseEntity<>(_service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing Account", tags = "Account")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            }
    )
    @PutMapping(value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public AccountResponse update(String id, @RequestBody AccountRequest request) {
        validator.validate(id, request);
        return _service.update(UUID.fromString(id), request);
    }

    @Operation(summary = "Delete an existing Account", tags = "Account")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500"),
            }
    )
    @DeleteMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity delete(String id) {
        validator.validate(id);
        _service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
