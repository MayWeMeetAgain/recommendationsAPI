package com.annieryannel.recommendationsapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {
    public static String ADMIN = "ROLE_ADMIN";

    @Id
    @Column(name = "role_id")
    private Short id;

    @Column(name = "role_name")
    private String role;

    @Override
    public String getAuthority() {
        return getRole();
    }
}
