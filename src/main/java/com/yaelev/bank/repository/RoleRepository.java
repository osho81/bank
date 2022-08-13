package com.yaelev.bank.repository;

import com.yaelev.bank.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Security component'
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String roleName);
}
