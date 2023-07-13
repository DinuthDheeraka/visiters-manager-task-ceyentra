package com.ceyentra.springboot.visitersmanager.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserPermission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    RECEPTIONIST_READ("receptionist:read"),
    RECEPTIONIST_UPDATE("receptionist:update"),
    RECEPTIONIST_CREATE("receptionist:create"),
    RECEPTIONIST_DELETE("receptionist:delete");

    @Getter
    private final String permission;
}
