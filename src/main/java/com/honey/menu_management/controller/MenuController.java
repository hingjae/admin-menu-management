package com.honey.menu_management.controller;

import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/menus")
@RequiredArgsConstructor
@Controller
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public String menuTree() {
        return "menus";
    }
}
