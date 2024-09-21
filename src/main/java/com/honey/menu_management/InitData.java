package com.honey.menu_management;

import com.honey.menu_management.controller.dto.UserCreateForm;
import com.honey.menu_management.entity.User;
import com.honey.menu_management.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class InitData {
    private final UserService userService;

    @PostConstruct
    @Transactional
    public void saveInitUser() {
        try {
            User defaultUser = userService.findById("user1");
        } catch (EntityNotFoundException e) {
            userService.create(
                    UserCreateForm.builder()
                            .username("user1")
                            .password("pw1")
                            .build()
            );
        }
    }
}
