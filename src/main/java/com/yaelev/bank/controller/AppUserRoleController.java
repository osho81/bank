package com.yaelev.bank.controller;

// Spring Security component

import com.yaelev.bank.model.AppUser;
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

    @Autowired
    public AppUserRoleController(AppUserRoleService appUserRoleService) {
        this.appUserRoleService = appUserRoleService;
    }

    @GetMapping("/all") // http://localhost:8080/api/v1/user/all
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(appUserRoleService.getAppUsers());
    }

    @GetMapping("/{username}") // http://localhost:8080/api/v1/user/username-arg
    public ResponseEntity<AppUser> getAppUser(@PathVariable String username) {
        return ResponseEntity.ok().body(appUserRoleService.getAppUser(username));
    }

    @PostMapping
    public void registerNewAppUser(@RequestBody AppUser appUser) {
        appUserRoleService.saveAppUser(appUser);
    }

    // TODO: Update/PUT mapping/method for AppUser

    @DeleteMapping("/{username}")
    public void deleteAppUser(@PathVariable String username) {
        appUserRoleService.deleteByUsername(username);
    }


}
