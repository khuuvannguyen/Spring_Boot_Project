package com.vannguyen.SpringBootProject.domain.services.interfaces;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    List<AccountResponse> get();
    AccountResponse get(String username);
    AccountResponse create(AccountRequest request);
    AccountResponse update(UUID id, AccountRequest request);
    void delete(UUID id);
}
