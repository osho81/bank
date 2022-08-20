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


    // Define authentication type (in memory, authenticationManager etc)


    // Define security: Customize login, path authorization, exceptions etc
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()

                // Customize specific paths' security requirements;
                // See loaded user authorities in AppUserRoleService class
                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.GET, "/customer/**").permitAll() // Anyone can GET
                .antMatchers(HttpMethod.POST, "/api/v*/**").hasRole("EMPLOYEE")
//                .antMatchers("/api/v*/t-account/**").hasAuthority("ROLE_ADMIN") // Restrict certain path
                .antMatchers("/api/v*/t-account/**").hasRole("ADMIN") // (ROLE_ is appended)
//                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                .anyRequest().authenticated() // Must be logged in for any request
                .and() //Login Form configuration for all others

//                .formLogin()
//                .loginPage("/login").permitAll() // Render webpage for this path as login
//                .and()

                // Specify further behaviours
                .logout().invalidateHttpSession(true)
                .and()

                .csrf().disable(); // Disable cross-site request forgery

                //.formLogin();

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
