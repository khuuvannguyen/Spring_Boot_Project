package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository _accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private List<AccountResponse> toList(List<Account> list) {
        List<AccountResponse> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public List<AccountResponse> get() {
        List<Account> list = _accountRepository.findAll();
        return this.toList(list);
    }

    @Override
    public AccountResponse get(String username) {
        Account entity = _accountRepository.findByUsername(username);
        if (entity != null)
            return entity.toResponse();
        return null;
    }

    @Override
    public AccountResponse create(AccountRequest request) {
        Account existingEntity = _accountRepository.findByUsername(request.getUsername());
        if (existingEntity != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, request.getUsername() + " is existing");
        Account entity = new Account();
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRoles(request.getRoles());
        Account saved = _accountRepository.save(entity);
        return saved.toResponse();
    }

    @Override
    public AccountResponse update(UUID id, AccountRequest request) {
        Optional<Account> entityFromDB = _accountRepository.findById(id);
        if (!entityFromDB.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Account entity = entityFromDB.get();
        entity.update(request);
        Account saved = _accountRepository.save(entity);
        return saved.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Account> entity = _accountRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        _accountRepository.deleteById(id);
    }
}
