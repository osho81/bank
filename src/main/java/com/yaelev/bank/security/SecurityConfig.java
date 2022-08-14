package com.yaelev.bank.security;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Spring Security component

// WebSecurityConfigurerAdapter is deprecated; update to newer approach asap:
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter


// Deprecated approach to spring sec config
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
}

