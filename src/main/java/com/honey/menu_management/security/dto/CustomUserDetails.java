package com.honey.menu_management.security.dto;

import com.honey.menu_management.entity.Authority;
import com.honey.menu_management.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {
    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails from(User user) {
        return CustomUserDetails.builder()
                .username(user.getId())
                .password(user.getPassword())
                .authorities(
                        user.getAuthorities().stream()
                                .map(Authority::name)
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toUnmodifiableSet())
                )
                .build();
    }

    @Builder
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
