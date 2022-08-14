package com.yaelev.bank.controller;

// Spring Security component

import com.yaelev.bank.model.AppUser;
import com.yaelev.bank.repository.AppUserRepository;
import com.yaelev.bank.repository.RoleRepository;
import com.yaelev.bank.service.AppUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/v1/user") // i.e. http://localhost:8080/api/v1
public class AppUserRoleController {

    private final AppUserRoleService appUserRoleService;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AppUserRoleController(AppUserRoleService appUserRoleService,
                                 AppUserRepository appUserRepository,
                                 RoleRepository roleRepository) {
        this.appUserRoleService = appUserRoleService;
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/all") // http://localhost:8080/api/v1/user/all
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(appUserRoleService.getAppUsers());
    }

    @GetMapping("/{username}") // http://localhost:8080/api/v1/user/username-arg
    public ResponseEntity<AppUser> getAppUser(@PathVariable String username) {
        return ResponseEntity.ok().body(appUserRoleService.getAppUser(username));
    }


}
