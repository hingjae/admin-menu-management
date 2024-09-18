package com.honey.menu_management.controller;

import com.honey.menu_management.api.ApiResponse;
import com.honey.menu_management.controller.dto.MenuResponse;
import com.honey.menu_management.controller.dto.MenuResponseList;
import com.honey.menu_management.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenuRestController {
    private final MenuService menuService;

    @GetMapping("/api/menus")
    public ApiResponse<List<MenuResponse>> findAll() {
        MenuResponseList menuResponseList = MenuResponseList.from(menuService.findAll());

        return ApiResponse.ok(menuResponseList.getData());
    }
}
