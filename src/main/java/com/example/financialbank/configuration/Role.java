package com.example.financialbank.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.financialbank.configuration.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),

    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    )

    ;

    @Getter
    //cada Role possui um conjunto de permissões
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<>(
                getPermissions()
                        .stream()
                        .map(permission ->
                                new SimpleGrantedAuthority(permission.name()))
                        .toList()
        );
        authorities.add(
                new SimpleGrantedAuthority("ROLE_" + this.name())
        );
        return authorities;
    }
}
