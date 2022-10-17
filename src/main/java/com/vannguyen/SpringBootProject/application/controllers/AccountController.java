package com.vannguyen.SpringBootProject.application.controllers;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    IAccountService _service;

    @GetMapping
    public List<AccountResponse> get() {
        return _service.get();
    }

    @GetMapping(params = "username")
    public AccountResponse get(String username) {
        return _service.get(username);
    }

    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest request) {
        return _service.create(request);
    }

    @PutMapping(params = "id")
    public AccountResponse update(String id, @RequestBody AccountRequest request) {
        return _service.update(UUID.fromString(id), request);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity delete(String id) {
        _service.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
