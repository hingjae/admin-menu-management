package com.honey.menu_management.api;

import com.honey.menu_management.api.dto.HierarchyMenuSetResponse;
import com.honey.menu_management.api.dto.MenuDetailResponse;
import com.honey.menu_management.api.dto.MenuResponse;
import com.honey.menu_management.api.dto.TreeJsMenuResponse;
import com.honey.menu_management.controller.dto.DragAndDropRequest;
import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
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

    @GetMapping("/api/menus/{menuId}")
    public ApiResponse<MenuDetailResponse> getMenuById(@PathVariable("menuId") Integer menuId) {
        MenuDetailResponse menu = MenuDetailResponse.from(menuService.findById(menuId));

        return ApiResponse.ok(menu);
    }

    @PatchMapping("/api/menus/{menuId}")
    public ApiResponse<Integer> dragAndDropMenu(@PathVariable("menuId") Integer menuId, @RequestBody DragAndDropRequest request) {
        return ApiResponse.ok(menuId);
    }
}
