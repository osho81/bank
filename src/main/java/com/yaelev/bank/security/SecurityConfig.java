package com.yaelev.bank.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

// Spring Security component

// WebSecurityConfigurerAdapterDeprecated is deprecated
// New approach since 2022 (spring security v 5.7.+); as implemented hereunder
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override // Deprecated approach
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

    @Bean // Replacing AuthenticationManagerBuild method above in v 5.7.+ approach
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean // SecurityFilterChain filterChain in v 5.7.+ approach
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Disable Cross-Site Request Forgery
        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.authorizeRequests()
//                // .antMatchers("/api/v*/**) //rearrange url-structure of bank site
//                .anyRequest()
//                .permitAll();
        // http.addFilter(new AuthenticationFilter(authenticationManagerBean()));

        http.apply(customDsl());

        return http.build(); // Build line added in v 5.7.+ approach
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManagerBean();
    }
}

