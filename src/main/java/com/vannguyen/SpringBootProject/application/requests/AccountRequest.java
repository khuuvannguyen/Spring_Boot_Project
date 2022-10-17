package com.vannguyen.SpringBootProject.application.requests;

import com.vannguyen.SpringBootProject.domain.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private String roles;
}
