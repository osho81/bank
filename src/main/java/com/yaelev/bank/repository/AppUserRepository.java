package com.yaelev.bank.repository;

import com.yaelev.bank.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Security component
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String username);
    AppUser findAppUserByUserEmail(String userEmail);
}
