package com.honey.menu_management.entity;

import com.honey.menu_management.converter.AuthorityConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {
    @Id
    private String id;

    private String password;

    @Convert(converter = AuthorityConverter.class)
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public User(String id, String password, Set<Authority> authorities) {
        this.id = id;
        this.password = password;
        this.authorities = authorities;
    }
}
