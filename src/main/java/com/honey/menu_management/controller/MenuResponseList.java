package com.honey.menu_management.controller;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuResponseList {
    private final List<MenuResponse> data;

    @Builder
    public MenuResponseList(List<MenuResponse> data) {
        this.data = data;
    }

    public static MenuResponseList from(List<Menu> menus) {
        return MenuResponseList.builder()
                .data(
                        menus.stream()
                                .map(MenuResponse::from)
                                .toList()
                )
                .build();
    }
}
