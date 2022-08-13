package com.yaelev.bank.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

// Spring Security component

@Entity
@Data // Lombok getters/setters etc
@NoArgsConstructor // Lombok constructors
@AllArgsConstructor
// @Builder(toBuilder = true)
public class AppUser {
    @Id
    @SequenceGenerator(
            name = "appuser_sequence",
            sequenceName = "appuser_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_sequence")
    private long id;
    private String name;
    private String username;
    private String password;

    // @Singular
    @ManyToMany(fetch = FetchType.LAZY) // Add mappedBy="roleName" to avoid join-table
    private Collection<Role> roles; // = new ArrayList<>()


    // Customized constructor because of lombok/hibernate issue
    public AppUser(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }
}