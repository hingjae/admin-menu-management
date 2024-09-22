package com.honey.menu_management.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DragAndDropRequest {
    private final Integer id;

    private final Integer oldParent;

    private final Integer newParent;

    private final Integer oldOrder;

    private final Integer newOrder;

    @Builder
    public DragAndDropRequest(Integer id, Integer oldParent, Integer newParent, Integer oldOrder, Integer newOrder) {
        this.id = id;
        this.oldParent = oldParent;
        this.newParent = newParent;
        this.oldOrder = oldOrder;
        this.newOrder = newOrder;
    }
}
