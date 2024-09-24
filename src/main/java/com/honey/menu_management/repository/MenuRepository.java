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

    //TODO parentId가 null인 경우 고려하기
    @Modifying
    @Query("update Menu m" +
            " set m.menuOrder = m.menuOrder - 1" +
            " where m.parent.id = :parentId" +
            " and m.menuOrder between :oldOrder and :newOrder")
    int bulkOrderMinus(@Param("parentId") Integer parentId,
                       @Param("oldOrder") Integer oldOrder,
                       @Param("newOrder") Integer newOrder);

    @Modifying
    @Query("update Menu m" +
            " set m.menuOrder = m.menuOrder + 1" +
            " where m.parent.id = :parentId" +
            " and m.menuOrder between :newOrder and :oldOrder")
    int bulkOrderPlus(@Param("parentId") Integer parentId,
                      @Param("oldOrder") Integer oldOrder,
                      @Param("newOrder") Integer newOrder);

    @Modifying
    @Query("update Menu m" +
            " set m.menuOrder = m.menuOrder - 1" +
            " where m.parent.id = :oldParent" +
            " and m.menuOrder > :oldOrder")
    int bulkOrderMinus(@Param("oldParent") Integer oldParent, @Param("oldOrder") Integer oldOrder);

    @Modifying
    @Query("update Menu m" +
            " set m.menuOrder = m.menuOrder + 1" +
            " where m.parent.id = :newParent" +
            " and m.menuOrder >= :newOrder")
    int bulkOrderPlus(@Param("newParent") Integer newParent, @Param("newOrder") Integer newOrder);
}
