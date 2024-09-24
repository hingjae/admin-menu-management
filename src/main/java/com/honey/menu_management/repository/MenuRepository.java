package com.honey.menu_management.repository;

import com.honey.menu_management.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer>, MenuBulkRepositoryCustom {
    List<Menu> findAllByOrderByMenuOrder();

    @Query("select m" +
            " from Menu m" +
            " left join fetch m.parent" +
            " where m.id = :id")
    Optional<Menu> findByIdWithParent(@Param("id") Integer id);

    List<Menu> findAllByParentIsNullOrderByMenuOrder();
}
