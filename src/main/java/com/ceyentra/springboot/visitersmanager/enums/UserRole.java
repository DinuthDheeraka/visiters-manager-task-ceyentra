package com.ceyentra.springboot.visitersmanager.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ceyentra.springboot.visitersmanager.enums.UserPermission.*;

@RequiredArgsConstructor
public enum UserRole {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    RECEPTIONIST_READ,
                    RECEPTIONIST_UPDATE,
                    RECEPTIONIST_DELETE,
                    RECEPTIONIST_CREATE
            )
    ),
    RECEPTIONIST(
            Set.of(
                    RECEPTIONIST_READ,
                    RECEPTIONIST_UPDATE,
                    RECEPTIONIST_DELETE,
                    RECEPTIONIST_CREATE
            )
    ),

    ;

    @Getter
    private final Set<UserPermission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
