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

                .csrf().disable() // Disable cross-site request forgery

                // Customize specific paths' security requirements;
                // See loaded user authorities in AppUserRoleService class
                .authorizeHttpRequests()



//                .antMatchers(HttpMethod.GET, "/customer/**").permitAll() // Anyone can GET customers
                .antMatchers(HttpMethod.POST, "/api/v*/**").hasRole("EMPLOYEE")
//                .antMatchers("/api/v*/t-account/**").hasAuthority("ROLE_ADMIN") // Restrict certain path
                .antMatchers("/api/v*/t-account/**").hasRole("ADMIN") // (ROLE_ is appended)
//                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                .anyRequest().authenticated() // Must be logged in for any request
                .and()

                // Example spring security default login form procedure
                .formLogin().permitAll() // (redirects to/starts at:) http://localhost:8080/login
                //.loginPage("/api/v1/someCustomePage").permitAll() // Eventual custom login page
                .and()

                .logout().invalidateHttpSession(false);

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
