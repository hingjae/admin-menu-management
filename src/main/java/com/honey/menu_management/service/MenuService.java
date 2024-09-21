package com.honey.menu_management.service;

import com.honey.menu_management.entity.Menu;
import com.honey.menu_management.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return menuRepository.save(menu);
    }

    @Transactional
    public void modify(Integer id, MenuModify menuModify) {
        Menu menu = menuRepository.findByIdWithParent(id)
                .orElseThrow(EntityNotFoundException::new);

        menu.modify(menuModify);
    }
}
