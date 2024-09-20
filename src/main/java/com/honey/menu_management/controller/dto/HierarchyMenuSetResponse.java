package com.honey.menu_management.controller.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class HierarchyMenuSetResponse {
    private final Set<MenuResponse> data;

    @Builder
    public HierarchyMenuSetResponse(Set<MenuResponse> data) {
        this.data = data;
    }

    public static HierarchyMenuSetResponse from(List<Menu> menuEntities) {
        List<MenuResponse> menus = menuEntities.stream()
                .map(MenuResponse::from)
                .toList();

        // Map<Integer, MenuResponse>와 List<MenuResponse>는 같은 MenuResponse를 참조한다.
        Map<Integer, MenuResponse> menuMap = menus.stream()
                .collect(Collectors.toMap(MenuResponse::getId, menu -> menu));

        Set<MenuResponse> rootMenus = new TreeSet<>();

        for (MenuResponse menu : menus) {
            if (menu.getParentMenuId() != null) {
                MenuResponse parentMenu = menuMap.get(menu.getParentMenuId());
                parentMenu.getChildren().add(menu);
            } else {
                rootMenus.add(menu);
            }
        }

        return HierarchyMenuSetResponse.builder()
                .data(rootMenus)
                .build();
    }
}
