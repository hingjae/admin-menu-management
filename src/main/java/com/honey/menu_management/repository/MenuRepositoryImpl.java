package com.honey.menu_management.repository;

import com.honey.menu_management.entity.QMenu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.honey.menu_management.entity.QMenu.menu;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuBulkRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public long bulkOrderMinusBetween(Integer parentId, Integer oldOrder, Integer newOrder) {
        return query
                .update(menu)
                .set(menu.menuOrder, menu.menuOrder.add(-1))
                .where(
                        parentIdEq(parentId),
                        menu.menuOrder.between(oldOrder, newOrder)
                )
                .execute();
    }

    @Override
    public long bulkOrderPlusBetween(Integer parentId, Integer oldOrder, Integer newOrder) {
        return query
                .update(menu)
                .set(menu.menuOrder, menu.menuOrder.add(1))
                .where(
                        parentIdEq(parentId),
                        menu.menuOrder.between(newOrder, oldOrder)
                )
                .execute();
    }

    @Override
    public long bulkOrderMinusGreaterThan(Integer parentId, Integer order) {
        return query
                .update(menu)
                .set(menu.menuOrder, menu.menuOrder.add(-1))
                .where(
                        parentIdEq(parentId),
                        menu.menuOrder.gt(order)
                )
                .execute();
    }

    @Override
    public long bulkOrderPlusLessThan(Integer parentId, Integer order) {
        return query
                .update(menu)
                .set(menu.menuOrder, menu.menuOrder.add(1))
                .where(
                        parentIdEq(parentId),
                        menu.menuOrder.goe(order)
                )
                .execute();
    }

    private BooleanExpression parentIdEq(Integer parentId) {
        if (parentId == null) {
            return menu.parent.isNull();
        } else {
            return menu.parent.id.eq(parentId);
        }
    }
}
