package com.yaelev.bank.repository;

import com.yaelev.bank.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Security component
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // AppUser findAppUserByUsername(String username); // Reserve method during development
    AppUser findAppUserByEmail(String email);
}
