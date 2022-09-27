package com.yaelev.bank.service;

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.model.Customer;
import com.yaelev.bank.model.Role;
import com.yaelev.bank.repository.AppUserRepository;
import com.yaelev.bank.repository.CustomerRepository;
import com.yaelev.bank.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    // Use for connecting Customer with useremail/username
    private final CustomerRepository customerRepository;

    @Autowired // Added this constructor; lombok/hibernate issue
    public AppUserRoleService(AppUserRepository appUserRepository,
                              RoleRepository roleRepository,
                              BCryptPasswordEncoder bCryptPasswordEncoder,
                              CustomerRepository customerRepository) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customerRepository = customerRepository;
    }

        // Previous version, where AppUser table is unrelated to customer table:
//    @Override // Implementing UserDetailsService & this method is part of Security setup
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AppUser appUser = appUserRepository.findAppUserByUsername(username);
//        if (appUser == null) {
//            log.error("User not found");
//            throw new UsernameNotFoundException("User not found");
//        } else {
//            log.info("User found");
//        }
//        // Authorities based on db-roles for user; to be used in authentication process
//        // Each role for the loaded user gets its authority
//        Collection<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
//        appUser.getRoles().forEach(role -> {
//            userAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//        });
//        // Return eventual AppUser/password+roles/authorities, as current logged in User
//        return new User(appUser.getUsername(), appUser.getPassword(), userAuthorities);
//    }


    // Using customer table as users
    @Override // Implementing UserDetailsService & this method is part of Security setup
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Optional<Customer> customer = customerRepository.findByEmail(email);
        Customer customer = customerRepository.findCustomerByEmail(email);
        AppUser appUser = appUserRepository.findAppUserByEmail(customer.getEmail());
        if (appUser == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found");
        }
        // Authorities based on db-roles for user; to be used in authentication process
        // Each role for the loaded user gets its authority
        Collection<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            userAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        // Return eventual AppUser/password+roles/authorities, as current logged in User
        return new User(appUser.getEmail(), appUser.getPassword(), userAuthorities);
    }



    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }



    /////////////////// Crud API for AppUser //////////////////////

    public AppUser getAppUser(String email) {
        log.info("Retrieving AppUser from DataBase"); // Add more details with placeholders {} later
        // return appUserRepository.findAppUserByUsername(username);
        return appUserRepository.findAppUserByEmail(email);
    }

    public List<AppUser> getAppUsers() {
        log.info("Retrieving AppUsers from DataBase");
        return appUserRepository.findAll();
    }

    public AppUser saveAppUser(AppUser appUser) {
        log.info("Saving AppUser to DataBase");
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public Role saveRole(Role role) {
        log.info("Saving Role to DataBase");
        return roleRepository.save(role);
    }

    public void addAppUserRole(String email, String roleName) {
        log.info("Saving AppUser's role to DataBase");
        // AppUser user = appUserRepository.findAppUserByUsername(username);
        AppUser user = appUserRepository.findAppUserByEmail(email);
        Role role = roleRepository.findRoleByRoleName(roleName);
        // Get user's roles-list, and add this role:
        user.getRoles().add(role);
    }

    public void deleteByUsername(String email) {
        // long appUserId = appUserRepository.findAppUserByUsername(username).getId();
        long appUserId = appUserRepository.findAppUserByEmail(email).getId();
        appUserRepository.deleteById(appUserId);
    }

}
