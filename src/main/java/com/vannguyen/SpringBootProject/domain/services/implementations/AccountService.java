package com.vannguyen.SpringBootProject.domain.services.implementations;

import com.vannguyen.SpringBootProject.application.controllers.ProductController;
import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceExistingException;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import com.vannguyen.SpringBootProject.domain.services.interfaces.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public List<AccountResponse> get() {
        List<Account> list = _accountRepository.findAll();
        if (list.isEmpty())
            return new ArrayList<>();
        List<AccountResponse> result = new ArrayList<>();
        list.forEach(i -> result.add(i.toResponse()));
        return result;
    }

    @Override
    public AccountResponse get(String username) {
        Account entity = _accountRepository.findByUsername(username);
        if (entity != null)
            return entity.toResponse();
        throw new ResourceNotFoundException("Not found username: " + username);
    }

    @Override
    public AccountResponse create(AccountRequest request) {
        Account existingEntity = _accountRepository.findByUsername(request.getUsername());
        if (existingEntity != null)
            throw new ResourceExistingException(request.getUsername() + " is existing");
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
        if (!entityFromDB.isPresent())
            throw new ResourceNotFoundException("Account not found");
        Account entity = entityFromDB.get();
        entity.update(request);
        Account saved = _accountRepository.save(entity);
        return saved.toResponse();
    }

    @Override
    public void delete(UUID id) {
        Optional<Account> entity = _accountRepository.findById(id);
        if (!entity.isPresent())
            throw new ResourceNotFoundException("Account not found");
        _accountRepository.deleteById(id);
    }
}
