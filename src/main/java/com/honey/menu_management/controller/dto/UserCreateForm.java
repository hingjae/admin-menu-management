package com.honey.menu_management.controller.dto;

import com.honey.menu_management.entity.Authority;
import com.honey.menu_management.entity.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserCreateForm {
    private final String username;

    private final String password;

    public UserCreateForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User toEntity(String encodedPassword) {
        return User.builder()
                .id(username)
                .password(encodedPassword)
                .authorities(Set.of(Authority.USER))
                .build();
    }
}
