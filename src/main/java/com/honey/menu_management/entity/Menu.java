package com.honey.menu_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    private String name;

    private Integer menuOrder;

    private String icon;

    @OneToMany(mappedBy = "parent")
    private List<Menu> subMenus = new ArrayList<>();

    public void modify(String menuName, String menuIcon) {
        this.name = menuName;
        this.icon = menuIcon;
    }

    @Builder
    public Menu(Integer id, Menu parent, String name, Integer menuOrder, String icon, List<Menu> subMenus) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.menuOrder = menuOrder;
        this.icon = icon;
        this.subMenus = subMenus;
    }

    public void setParentAndOrder(Menu parent, Integer newOrder) {
        this.parent = parent;
        this.menuOrder = newOrder;
    }

    public void setOrder(Integer order) {
        this.menuOrder = order;
    }
}
