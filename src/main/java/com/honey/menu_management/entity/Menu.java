package com.honey.menu_management.entity;

import com.honey.menu_management.service.MenuModify;
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

    @Setter
    @OneToMany(mappedBy = "parent")
    private List<Menu> subMenus = new ArrayList<>();

    public void modify(MenuModify menuModify) {

    }

    @Builder
    public Menu(Integer id, Menu parent, String name, Integer menuOrder, List<Menu> subMenus) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.menuOrder = menuOrder;
        this.subMenus = subMenus;
    }

    public boolean isRoot() {
        return parent == null;
    }
}
