package com.honey.menu_management.controller;

import com.honey.menu_management.controller.dto.MenuForm;
import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/menus")
@RequiredArgsConstructor
@Controller
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public String menuTree() {
        return "menus";
    }

    @GetMapping("/create")
    public String createMenuForm(@ModelAttribute("menuForm") MenuForm menuForm, Model model) {
        menuForm.setIcon("fa-solid fa-mitten");
        model.addAttribute("isCreate", true);
        return "menu-form";
    }

    @PostMapping("/create")
    public String createMenu(@ModelAttribute("menuForm") MenuForm menuForm) {
        menuService.create(menuForm.toEntity());

        return "redirect:/admin/menus";
    }

    @GetMapping("/{menuId}/modify")
    public String modifyMenuForm(@PathVariable("menuId") Integer menuId, Model model) {
        MenuForm form = MenuForm.from(menuService.findById(menuId));
        model.addAttribute("menuForm", form);
        model.addAttribute("menuId", menuId);
        model.addAttribute("isCreate", false);
        return "menu-form";
    }

    @PostMapping("/{menuId}/modify")
    public String modifyMenu(@PathVariable("menuId") Integer menuId, @ModelAttribute("menuForm") MenuForm menuForm) {
        menuService.modify(menuId, menuForm.getName(), menuForm.getIcon());
        return "redirect:/admin/menus";
    }
}
