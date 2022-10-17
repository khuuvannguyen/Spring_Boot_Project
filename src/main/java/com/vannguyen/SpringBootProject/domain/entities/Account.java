package com.vannguyen.SpringBootProject.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vannguyen.SpringBootProject.application.requests.AccountRequest;
import com.vannguyen.SpringBootProject.application.responses.AccountResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tbl_account")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;

    private String username;

    private String password;

    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    private Set<Product> createdProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.PERSIST)
    private Set<Product> updatedProducts;

    public void update(AccountRequest request) {
        password = request.getPassword();
        roles = request.getRoles();
    }

    public AccountResponse toResponse() {
        return new AccountResponse(id, username);
    }
}
