package com.honey.menu_management.service;

import com.honey.menu_management.entity.Menu;
import com.honey.menu_management.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public List<Menu> findAllByOrder() {
        return menuRepository.findAllByOrderByMenuOrder();
    }

    public Menu findById(Integer id) {
        return menuRepository.findByIdWithParent(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Menu create(Menu menu) {
        Integer lastMenuOrder = menuRepository.findAllByParentIsNullOrderByMenuOrder()
                .getLast()
                .getMenuOrder();

        menu.setOrder(lastMenuOrder + 1);
        return menuRepository.save(menu);
    }

    @Transactional
    public void modify(Integer id, String menuName, String menuIcon) {
        Menu menu = menuRepository.findByIdWithParent(id)
                .orElseThrow(EntityNotFoundException::new);

        menu.modify(menuName, menuIcon);
    }

    @Transactional
    public void dragAndDrop(Integer id, Integer oldParentId, Integer newParentId, Integer oldOrder, Integer newOrder) {
        if (Objects.equals(oldParentId, newParentId)) {
            if (oldOrder > newOrder) {
                menuRepository.bulkOrderPlusBetween(newParentId, newOrder, oldOrder);
            } else {
                menuRepository.bulkOrderMinusBetween(newParentId, oldOrder, newOrder);
            }
        } else {
            menuRepository.bulkOrderMinusGreaterThan(oldParentId, oldOrder);
            menuRepository.bulkOrderPlusLessThan(newParentId, newOrder);
        }

        Menu menu = menuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Menu parentMenu = null;
        if (newParentId != null) {
            parentMenu = menuRepository.findById(newParentId)
                    .orElseThrow(EntityNotFoundException::new);
        }

        menu.setParentAndOrder(parentMenu, newOrder);
    }
}
