package com.honey.menu_management.api;

import com.honey.menu_management.api.dto.MenuResponse;
import com.honey.menu_management.api.dto.HierarchyMenuSetResponse;
import com.honey.menu_management.api.dto.TreeJsMenuResponse;
import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class MenuRestController {
    private final MenuService menuService;

    @GetMapping("/api/menus")
    public ApiResponse<Set<MenuResponse>> getMenus() {
        HierarchyMenuSetResponse hierarchyMenuSetResponse = HierarchyMenuSetResponse.from(menuService.findAll());
        return ApiResponse.ok(hierarchyMenuSetResponse.getData());
    }

    @GetMapping("/api/menu-tree")
    public ApiResponse<List<TreeJsMenuResponse>> getMenuV2() {
        List<TreeJsMenuResponse> menus = menuService.findAllByOrder().stream()
                .map(TreeJsMenuResponse::from)
                .toList();

        return ApiResponse.ok(menus);
    }
}
