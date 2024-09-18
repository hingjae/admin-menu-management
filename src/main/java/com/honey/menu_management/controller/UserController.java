package com.honey.menu_management.controller;

import com.honey.menu_management.controller.dto.UserCreateForm;
import com.honey.menu_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/users/new")
    public String signUpView(@ModelAttribute("userCreateForm") UserCreateForm form, Model model) {
        return "sign-up";
    }

    @PostMapping("/users/new")
    public String signUp(@ModelAttribute("userCreateForm") UserCreateForm form) {
        String userId = userService.create(form);
        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/{userId}")
    public String userView(@PathVariable String userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return "user";
    }
}
