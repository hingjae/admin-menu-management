package com.honey.menu_management.repository;

import com.honey.menu_management.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findAllByOrderByMenuOrder();
}
