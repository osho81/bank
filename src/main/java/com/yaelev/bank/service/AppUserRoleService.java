package com.yaelev.bank.service;

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.model.Role;
import com.yaelev.bank.repository.AppUserRepository;
import com.yaelev.bank.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

// Spring Security component
// Service class for both users and roles

@Service
@Transactional
@RequiredArgsConstructor // Lombok, constructor with required para types
@Slf4j // Lombok logger
public class AppUserRoleService {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;

    public AppUser saveAppUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addAppUserRole(String username, String roleName) {

    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }



}
