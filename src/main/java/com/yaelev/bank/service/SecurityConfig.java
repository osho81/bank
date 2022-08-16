package com.yaelev.bank.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    @Bean // Customize login, authentication procedures etc
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.GET, "/t-account/**").permitAll() // Anyone can GET
//                .antMatchers(HttpMethod.POST, "/etc...").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/**").hasRole("ROLE_ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/someOtherPath/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().invalidateHttpSession(true)
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
