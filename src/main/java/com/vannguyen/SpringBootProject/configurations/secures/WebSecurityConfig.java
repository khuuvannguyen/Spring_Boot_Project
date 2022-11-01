package com.vannguyen.SpringBootProject.configurations.secures;

import com.vannguyen.SpringBootProject.configurations.constants.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests((requests) -> {
            try {
                requests
                        .antMatchers(Permission.NONE_PERMISSIONS).permitAll()
                        .antMatchers(HttpMethod.GET, Permission.READ_PERMISSIONS).hasAuthority(Permission.READ)
                        .antMatchers(HttpMethod.PUT, Permission.WRITE_PERMISSIONS).hasAuthority(Permission.WRITE)
                        .antMatchers(HttpMethod.POST, Permission.WRITE_PERMISSIONS).hasAuthority(Permission.WRITE)
                        .antMatchers(HttpMethod.DELETE, Permission.WRITE_PERMISSIONS).hasAuthority(Permission.WRITE)
                        .anyRequest().authenticated()
                        .and().httpBasic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) ->
//                web.ignoring()
//                        .antMatchers("/api/auth/**")
//                        .antMatchers("/v3/api-docs/**")
//                        .antMatchers("configuration/**")
//                        .antMatchers("/swagger*/**")
//                        .antMatchers("/webjars/**")
//                        .antMatchers("/swagger-ui/**");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
