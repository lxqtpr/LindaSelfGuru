package dev.lxqtpr.lindaSelfGuru.Domain.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleEnum implements GrantedAuthority {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}

