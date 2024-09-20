package com.honey.menu_management.api;

import com.honey.menu_management.controller.dto.MenuResponse;
import com.honey.menu_management.controller.dto.HierarchyMenuSetResponse;
import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
public class MenuRestController {
    private final MenuService menuService;

    @GetMapping("/api/menus")
    public ApiResponse<Set<MenuResponse>> findAll() {
        HierarchyMenuSetResponse hierarchyMenuSetResponse = HierarchyMenuSetResponse.from(menuService.findAll());
        return ApiResponse.ok(hierarchyMenuSetResponse.getData());
    }
}
