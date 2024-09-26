package com.honey.menu_management.controller.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MenuForm {
    private final String name;

    @Setter
    private String icon;

    @Builder
    public MenuForm(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public static MenuForm from(Menu menu) {
        return MenuForm.builder()
                .name(menu.getName())
                .icon(menu.getIcon())
                .build();
    }

    public Menu toEntity() {
        return Menu.builder()
                .parent(null)
                .name(name)
                .icon(icon)
                .build();
    }
}
