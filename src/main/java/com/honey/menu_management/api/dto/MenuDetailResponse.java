package com.honey.menu_management.api.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuDetailResponse {
    private final Integer id;

    private final String parentMenuName;

    private final String name;

    private final Integer menuOrder;

    private final String icon;

    @Builder
    public MenuDetailResponse(Integer id, String parentMenuName, String name, Integer menuOrder, String icon) {
        this.id = id;
        this.parentMenuName = parentMenuName;
        this.name = name;
        this.menuOrder = menuOrder;
        this.icon = icon;
    }

    public static MenuDetailResponse from(Menu menu) {
        return MenuDetailResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .parentMenuName(menu.getParent() != null ? menu.getParent().getName() : null)
                .menuOrder(menu.getMenuOrder())
                .icon(menu.getIcon())
                .build();
    }
}
