package com.honey.menu_management.api.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TreeJsMenuResponse {
    private final Integer id;

    private final String parent;

    private final String text;

    private final String icon;

    public static TreeJsMenuResponse from(Menu menu) {
        return TreeJsMenuResponse.builder()
                .id(menu.getId())
                .parent(menu.getParent() != null ? String.valueOf(menu.getParent().getId()) : "#")
                .text(menu.getName())
                .icon(menu.getIcon())
                .build();
    }

    @Builder
    public TreeJsMenuResponse(Integer id, String parent, String text, String icon) {
        this.id = id;
        this.parent = parent;
        this.text = text;
        this.icon = icon;
    }
}
