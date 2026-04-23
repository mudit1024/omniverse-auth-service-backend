package com.omniverse.auth_Service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collections;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final String email;

    public JwtAuthentication(String email) {
        super(Collections.emptyList());
        this.email = email;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}