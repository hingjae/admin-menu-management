package com.honey.menu_management.service;

import com.honey.menu_management.entity.Menu;
import com.honey.menu_management.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class MenuServiceTest {

    @Autowired private MenuService menuService;
    @Autowired private MenuRepository menuRepository;

    @Transactional
    @DisplayName("모든 메뉴를 가져온다.")
    @Test
    public void findAll() {
        menuInit();
        List<Menu> menus = menuService.findAll();

        assertThat(menus).hasSize(20);
    }

    @Transactional
    @DisplayName("메뉴를 id로 조회한다")
    @Test
    public void findById() {
        Menu menu = Menu.builder()
                .name("메뉴1")
                .menuOrder(1)
                .build();

        Integer menuId = menuRepository.save(menu).getId();

        Menu findMenu = menuService.findById(menuId);

        assertThat(findMenu.getName()).isEqualTo("메뉴1");
        assertThat(findMenu.getMenuOrder()).isEqualTo(1);
    }

    private void menuInit() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 1);
        Integer menu2Id = createAndSaveMenu("남성 의류", null, 2);
        Integer menu3Id = createAndSaveMenu("액세서리", null, 3);

        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);
        Menu menu2 = menuRepository.findById(menu2Id).orElse(null);
        Menu menu3 = menuRepository.findById(menu3Id).orElse(null);

        Integer menu4Id = createAndSaveMenu("아우터", menu1, 1);
        Integer menu5Id = createAndSaveMenu("상의", menu1, 2);
        Integer menu6Id = createAndSaveMenu("아우터", menu2, 1);
        Integer menu7Id = createAndSaveMenu("바지", menu2, 2);
        Integer menu8Id = createAndSaveMenu("가방", menu3, 1);
        Integer menu9Id = createAndSaveMenu("신발", menu3, 2);

        // 하위 메뉴의 하위 메뉴 생성
        Menu menu4 = menuRepository.findById(menu4Id).orElse(null);
        Menu menu5 = menuRepository.findById(menu5Id).orElse(null);
        Menu menu6 = menuRepository.findById(menu6Id).orElse(null);
        Menu menu7 = menuRepository.findById(menu7Id).orElse(null);
        Menu menu8 = menuRepository.findById(menu8Id).orElse(null);
        Menu menu9 = menuRepository.findById(menu9Id).orElse(null);

        createAndSaveMenu("코트", menu4, 1);
        createAndSaveMenu("자켓", menu4, 2);
        createAndSaveMenu("블라우스", menu5, 1);
        createAndSaveMenu("코트", menu6, 1);
        createAndSaveMenu("패딩", menu6, 2);
        createAndSaveMenu("청바지", menu7, 1);
        createAndSaveMenu("크로스백", menu8, 1);
        createAndSaveMenu("스니커즈", menu9, 1);
        createAndSaveMenu("부츠", menu9, 2);
        createAndSaveMenu("샌들", menu9, 3);
        createAndSaveMenu("셔츠", menu5, 2);
    }

    // 메뉴 객체를 생성하고 저장하는 메서드
    private Integer createAndSaveMenu(String name, Menu parent, Integer order) {
        Menu menu = Menu.builder()
                .name(name)
                .parent(parent)
                .menuOrder(order)
                .build();

        return menuRepository.save(menu)
                .getId();
    }

}