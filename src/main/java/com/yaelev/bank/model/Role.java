package com.yaelev.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Spring Security component
// (If max one role per user, this table could be a row in the AppUser-table)

@Entity
@Data // Lombok getters/setters etc
@NoArgsConstructor // Lombok constructors
@AllArgsConstructor
public class Role {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    private long id;

    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
