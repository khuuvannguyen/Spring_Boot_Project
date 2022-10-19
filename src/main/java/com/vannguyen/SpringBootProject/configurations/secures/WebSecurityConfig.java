package com.vannguyen.SpringBootProject.configurations.secures;

import com.vannguyen.SpringBootProject.domain.constants.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
public class WebSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests((requests) -> {
            try {
                requests
                        .antMatchers(Permission.NONE_PATTERNS).permitAll()
                        .antMatchers(HttpMethod.GET, Permission.READ_PATTERNS).hasAuthority(Permission.READ)
                        .antMatchers(HttpMethod.PUT, Permission.WRITE_PATTERNS).hasAuthority(Permission.WRITE)
                        .antMatchers(HttpMethod.POST, Permission.WRITE_PATTERNS).hasAuthority(Permission.WRITE)
                        .antMatchers(HttpMethod.DELETE, Permission.WRITE_PATTERNS).hasAuthority(Permission.WRITE)
                        .anyRequest().authenticated()
                        .and().httpBasic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
}
