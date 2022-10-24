package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.application.validators.AccountValidator;
import com.vannguyen.SpringBootProject.application.validators.Validator;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<AccountResponse> get() {
        return _service.get();
    }

    @GetMapping(value = "/{username}")
    public AccountResponse get(String username) {
        return _service.get(username);
    }

    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest request) {
        validator.validate(request);
        return _service.create(request);
    }

    @PutMapping(value = "/{id}")
    public AccountResponse update(String id, @RequestBody AccountRequest request) {
        validator.validate(id, request);
        return _service.update(UUID.fromString(id), request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(String id) {
        validator.validate(id);
        _service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
