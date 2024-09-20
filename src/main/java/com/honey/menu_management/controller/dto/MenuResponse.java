package com.honey.menu_management.controller.dto;

import com.honey.menu_management.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class MenuResponse implements Comparable<MenuResponse> {
    private final Integer id;

    private final Integer parentMenuId;

    private final String text;

    private final Integer menuOrder;

    private final Set<MenuResponse> children;

    @Builder
    public MenuResponse(Integer id, Integer parentMenuId, String text, Integer menuOrder, Set<MenuResponse> subMenus) {
        this.id = id;
        this.parentMenuId = parentMenuId;
        this.text = text;
        this.menuOrder = menuOrder;
        this.children = subMenus;
    }

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .parentMenuId(menu.getParent() != null ? menu.getParent().getId() : null)
                .text(menu.getName())
                .menuOrder(menu.getMenuOrder())
                .subMenus(new TreeSet<>())
                .build();
    }

    public boolean isRoot() {
        return parentMenuId == null;
    }

    public boolean isNotRoot() {
        return parentMenuId != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuResponse that = (MenuResponse) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public int compareTo(MenuResponse o) {
        return menuOrder.compareTo(o.getMenuOrder());
    }
}
