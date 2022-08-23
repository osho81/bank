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

                // Restrict certain path for certain users
                // See loaded user roles/uthorities in AppUserRoleService class
                .authorizeHttpRequests()

                .antMatchers("/api/v*/t-account/**").hasRole("ADMIN") // (ROLE_ is appended)
//                .antMatchers("/api/v*/t-account/**").hasAuthority("ROLE_ADMIN") // Alternative
//                .antMatchers("/api/v*/t-account/**").hasAnyRole("ADMIN", "USER") // Multiple

                .antMatchers(HttpMethod.GET, "/**").permitAll() // Anyone can GET anything
//                .antMatchers(HttpMethod.POST, "/api/v*/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.PUT, "/api/v*/**").hasRole("EMPLOYEE")
//                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")

                .anyRequest().authenticated() // User must be logged in for any request
                .and()

                // Default spring login page; redirects to/starts at http://localhost:8080/login
                .formLogin().permitAll()
                //.loginPage("/api/v1/someCustomedPage").permitAll() // Eventual custom login page
                .defaultSuccessUrl("http://localhost:3000/", true) // After login, start at React
                .and()

                // Default spring logout; logs out by redirecting to http://localhost:8080/logout
                .logout()
//                .logoutUrl("/perform_logout") // Eventual custom logout page

                // Invalidate sessions and eventual cookies
                .invalidateHttpSession(true);
//                .deleteCookies("JSESSIONID")

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
