package com.honey.menu_management.controller.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponse {
    private final String id;

    private final String text;

    private final List<MenuResponse> children;

    @Builder
    public MenuResponse(String id, String text, List<MenuResponse> children) {
        this.id = id;
        this.text = text;
        this.children = children;
    }

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(String.valueOf(menu.getId()))
                .text(menu.getName())
                .children(
                        menu.getSubMenus().stream()
                                .map(MenuResponse::from)
                                .toList()
                )
                .build();
    }
}
