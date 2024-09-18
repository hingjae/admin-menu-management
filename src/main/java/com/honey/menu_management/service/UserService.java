package com.honey.menu_management.service;

import com.honey.menu_management.controller.dto.UserCreateForm;
import com.honey.menu_management.entity.User;
import com.honey.menu_management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public String create(UserCreateForm form) {
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        log.info("encodedPassword: {}", encodedPassword);
        return userRepository.save(form.toEntity(encodedPassword))
                .getId();
    }
}
