package com.vannguyen.SpringBootProject.domain.services;

import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceExistingException;
import com.vannguyen.SpringBootProject.configurations.exceptions.ResourceNotFoundException;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import com.vannguyen.SpringBootProject.domain.services.implementations.AccountService;
import com.vannguyen.SpringBootProject.fakeDatas.fakeData;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountRepository _mockRepo;

    @Spy
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AccountService _mockService;

    private final String ADMIN = "admin";
    private final String USER = "user";

    @Before
    public void setUp() throws Exception {
        passwordEncoder = new Argon2PasswordEncoder();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void get_returnList() {
        given(_mockRepo.findAll()).willReturn(fakeData.getAccountList());

        List<AccountResponse> responses = _mockService.get();

        Assertions.assertThat(responses).isNotNull();
        Assertions.assertThat(responses.get(0).getUsername()).isEqualTo(this.ADMIN);
        Assertions.assertThat(responses.get(1).getUsername()).isEqualTo(this.USER);
    }

    @Test
    public void get_returnEmptyList() {
        given(_mockRepo.findAll()).willReturn(new ArrayList<>());

        List<AccountResponse> responses = _mockService.get();

        Assertions.assertThat(responses).isEmpty();
    }

    @Test
    public void getByUsername_returnObject() {
        given(_mockRepo.findByUsername(ArgumentMatchers.any()))
                .willReturn(fakeData.getAccount(this.ADMIN));

        AccountResponse response = _mockService.get(ArgumentMatchers.any());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getUsername()).isEqualTo(this.ADMIN);
    }

    @Test
    public void getByUsername_throw_notFoundUsername() {
        given(_mockRepo.findByUsername(ArgumentMatchers.any())).willReturn(null);

        Assertions.assertThatThrownBy(() -> {
            _mockService.get(this.ADMIN);
            throw new ResourceNotFoundException("Not found username: " + this.ADMIN);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void create_success() {
        given(_mockRepo.findByUsername(ArgumentMatchers.any()))
                .willReturn(null);
        given(_mockRepo.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getAccount(this.ADMIN));

        AccountResponse response = _mockService.create(fakeData.getAccountRequest("admin"));

        Mockito.verify(_mockRepo, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getUsername()).isEqualTo(this.ADMIN);
    }

    @Test
    public void create_throw_conflict() {
        given(_mockRepo.findByUsername(ArgumentMatchers.any()))
                .willReturn(fakeData.getAccount(this.ADMIN));

        Assertions.assertThatThrownBy(() -> {
            _mockService.create(fakeData.getAccountRequest(this.ADMIN));
            throw new ResourceExistingException(this.ADMIN + " is existing");
        }).isInstanceOf(ResourceExistingException.class);
    }

    @Test
    public void update_success() {
        given(_mockRepo.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getAccount(this.ADMIN)));
        given(_mockRepo.save(ArgumentMatchers.any()))
                .willReturn(fakeData.getAccount(this.ADMIN));

        AccountResponse response = _mockService.update(ArgumentMatchers.any(), fakeData.getAccountRequest(this.ADMIN));

        verify(_mockRepo, times(1)).save(ArgumentMatchers.any());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getUsername()).isEqualTo(this.ADMIN);
    }

    @Test
    public void update_throw_notFoundAccount() {
        given(_mockRepo.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            _mockService.update(ArgumentMatchers.any(), fakeData.getAccountRequest(this.ADMIN));
            throw new ResourceNotFoundException("Account not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void delete_success() {
        given(_mockRepo.findById(ArgumentMatchers.any()))
                .willReturn(Optional.of(fakeData.getAccount(this.ADMIN)));

        _mockService.delete(ArgumentMatchers.any());

        verify(_mockRepo, times(1)).deleteById(ArgumentMatchers.any());
    }

    @Test
    public void delete_throw_notFoundAccount() {
        given(_mockRepo.findById(ArgumentMatchers.any()))
                .willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            _mockService.delete(ArgumentMatchers.any());
            throw new ResourceNotFoundException("Account not found");
        }).isInstanceOf(ResourceNotFoundException.class);
    }
}