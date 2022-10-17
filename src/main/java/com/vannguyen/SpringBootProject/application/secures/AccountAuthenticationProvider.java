package com.vannguyen.SpringBootProject.application.secures;

import com.vannguyen.SpringBootProject.domain.entities.Account;
import com.vannguyen.SpringBootProject.domain.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AccountRepository repo;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Account account = repo.findByUsername(username);
        if (account == null) {
            throw new BadCredentialsException("Account not found");
        }
        if (encoder.matches(password, account.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, getAccountRoles(account.getRoles()));
        }
        throw new BadCredentialsException("Wrong password");
    }

    private List<GrantedAuthority> getAccountRoles(String roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        String[] array = roles.split(",");
        for (String role : array) {
            result.add(new SimpleGrantedAuthority(role.replaceAll("\\s", "")));
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
