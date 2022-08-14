package com.yaelev.bank.service;

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.model.Role;
import com.yaelev.bank.repository.AppUserRepository;
import com.yaelev.bank.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Spring Security component
// Service class for both User and Role classes

@Service
@Transactional
@RequiredArgsConstructor // Lombok, constructor with required para types
@Slf4j // Lombok logger
public class AppUserRoleService implements UserDetailsService {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // Use in get/save methods hereunder

    @Autowired // Added this constructor; lombok/hibernate issue
    public AppUserRoleService(AppUserRepository appUserRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override // Implements UserDetails & this method is part of SecurityConfig
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByUsername(username);
        if (appUser == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public AppUser saveAppUser(AppUser appUser) {
        log.info("Saving AppUser to DataBase"); // Add more details with placeholders later {}
        return appUserRepository.save(appUser);
    }

    public Role saveRole(Role role) {
        log.info("Saving Role to DataBase");
        return roleRepository.save(role);
    }

    public void addAppUserRole(String username, String roleName) {
        log.info("Saving AppUser's role to DataBase");
        AppUser user = appUserRepository.findAppUserByUsername(username);
        Role role = roleRepository.findRoleByRoleName(roleName);
        // Get user's roles-list, and add this role:
        user.getRoles().add(role);
    }

    public AppUser getAppUser(String username) {
        log.info("Retrieving AppUser from DataBase");
        return appUserRepository.findAppUserByUsername(username);
    }

    public List<AppUser> getAppUsers() {
        log.info("Retrieving AppUsers from DataBase");
        return appUserRepository.findAll();
    }

}
