package com.honey.menu_management.repository;

public interface MenuBulkRepositoryCustom {
    long bulkOrderMinusBetween(Integer parentId, Integer oldOrder, Integer newOrder);

    long bulkOrderPlusBetween(Integer parentId, Integer oldOrder, Integer newOrder);

    long bulkOrderMinusGreaterThan(Integer parentId, Integer order);

    long bulkOrderPlusLessThan(Integer parentId, Integer order);
}
