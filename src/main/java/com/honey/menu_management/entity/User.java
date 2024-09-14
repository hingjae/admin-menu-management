package com.honey.menu_management.entity;

import com.honey.menu_management.converter.AuthorityConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    private String id;

    private String password;

    @Convert(converter = AuthorityConverter.class)
    private Set<Authority> authorities = new HashSet<>();
}
