package com.example.mettle.feature.flag.utils;

import java.util.Arrays;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityContextFiller {

    public static void setContextWithRoles(String username, String... roles) {
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new User(username,
                "passw1", Arrays.stream(roles).map(SimpleGrantedAuthority::new).toList()), "", roles));
    }
}
