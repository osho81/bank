package com.yaelev.bank.repository;

import com.yaelev.bank.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Spring Security component
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByUsername(String username);
    // OR: User findAppUserByUsername(String username);
}
