package com.honey.menu_management.service;

import com.honey.menu_management.api.dto.MenuResponse;
import com.honey.menu_management.api.dto.HierarchyMenuSetResponse;
import com.honey.menu_management.controller.dto.DragAndDropRequest;
import com.honey.menu_management.controller.dto.MenuForm;
import com.honey.menu_management.entity.Menu;
import com.honey.menu_management.repository.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class MenuServiceTest {

    @Autowired private MenuService menuService;
    @Autowired private MenuRepository menuRepository;
    @Autowired
    private EntityManager em;

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

    @Transactional
    @DisplayName("모든 메뉴를 계층구조로 가져온다.")
    @Test
    public void findHierarchyMenuHierarchy() {
        menuInit();

        List<Menu> menus = menuService.findAll();

        HierarchyMenuSetResponse hierarchyMenuSetResponse = HierarchyMenuSetResponse.from(menus);

        assertThat(hierarchyMenuSetResponse.getData()).hasSize(3)
                .extracting("text")
                .containsExactly("액세서리", "남성 의류", "여성 의류");

        MenuResponse womenClothing = getParentMenu(hierarchyMenuSetResponse.getData(), "여성 의류");
        assertThat(womenClothing.getChildren())
                .hasSize(2)
                .extracting("text")
                .containsExactly("상의", "아우터");

        MenuResponse menClothing = getParentMenu(hierarchyMenuSetResponse.getData(), "남성 의류");
        assertThat(menClothing.getChildren())
                .hasSize(2)
                .extracting("text")
                .containsExactly("바지", "아우터");


        MenuResponse accessories = getParentMenu(hierarchyMenuSetResponse.getData(), "액세서리");
        assertThat(accessories.getChildren())
                .hasSize(2)
                .extracting("text")
                .containsExactly("신발", "가방");

        MenuResponse outer = getParentMenu(womenClothing.getChildren(), "아우터");
        assertThat(outer.getChildren())
                .hasSize(2)
                .extracting("text")
                .containsExactly("자켓", "코트");

        MenuResponse menOuter = getParentMenu(menClothing.getChildren(), "아우터");
        assertThat(menOuter.getChildren())
                .hasSize(2)
                .extracting("text")
                .containsExactly("코트", "패딩");

        MenuResponse shoes = getParentMenu(accessories.getChildren(), "신발");
        assertThat(shoes.getChildren())
                .hasSize(3)
                .extracting("text")
                .containsExactly("스니커즈", "샌들", "부츠");
    }

    @DisplayName("드래그앤 드롭으로 메뉴를 수정한다. 루트에서 루트로 이동.")
    @Transactional
    @Test
    public void setParentAndOrder1() {
        DragAndDropRequest request = setParentAndOrderMenuInit();

        menuService.dragAndDrop(request.getId(), request.getOldParent(), request.getNewParent(), request.getOldOrder(), request.getNewOrder());

        List<Menu> menus = menuRepository.findAllByParentIsNullOrderByMenuOrder();
        assertThat(menus).hasSize(3)
                .extracting("name")
                .containsExactly("여성 의류", "액세서리", "남성 의류");
    }

    private DragAndDropRequest setParentAndOrderMenuInit() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 2);
        Integer menu2Id = createAndSaveMenu("남성 의류", null, 1);
        Integer menu3Id = createAndSaveMenu("액세서리", null, 0);
        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);
        Menu menu2 = menuRepository.findById(menu2Id).orElse(null);
        Menu menu3 = menuRepository.findById(menu3Id).orElse(null);

        return getSetParentAndOrderRequest(menu1Id, null, null, menu1.getMenuOrder(), menu3.getMenuOrder());
    }

    @DisplayName("드래그앤 드롭으로 메뉴를 수정한다. 루트에서 하위로 이동.")
    @Transactional
    @Test
    public void setParentAndOrder2() {
        DragAndDropRequest request = setParentAndOrderMenuInit2();

        menuService.dragAndDrop(request.getId(), request.getOldParent(), request.getNewParent(), request.getOldOrder(), request.getNewOrder());

        em.flush();
        em.clear();

        Menu parentMenu = menuRepository.findById(request.getNewParent())
                .orElseThrow(EntityNotFoundException::new);
        List<Menu> newMenus = parentMenu.getSubMenus().stream()
                .sorted(Comparator.comparingInt(Menu::getMenuOrder))
                .toList();
        List<Menu> rootMenus = menuRepository.findAllByParentIsNullOrderByMenuOrder();
        assertThat(rootMenus).hasSize(2)
                .extracting("name")
                .containsExactly("남성 의류", "여성 의류");
        assertThat(newMenus).hasSize(3)
                .extracting("name")
                .containsExactly("액세서리", "상의", "아우터");
    }

    private DragAndDropRequest setParentAndOrderMenuInit2() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 2);
        Integer menu2Id = createAndSaveMenu("남성 의류", null, 1);
        Integer menu3Id = createAndSaveMenu("액세서리", null, 0);
        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);
        Menu menu2 = menuRepository.findById(menu2Id).orElse(null);
        Menu menu3 = menuRepository.findById(menu3Id).orElse(null);

        Integer menu4Id = createAndSaveMenu("아우터", menu1, 1);
        Integer menu5Id = createAndSaveMenu("상의", menu1, 0);

        return getSetParentAndOrderRequest(menu3Id, null, menu1Id, menu3.getMenuOrder(), 0);
    }

    @DisplayName("드래그앤 드롭으로 메뉴를 수정한다. 루트가 아닌 같은 레벨에서 이동")
    @Transactional
    @Test
    public void setParentAndOrder3() {
        DragAndDropRequest request = setParentAndOrderMenuInit3();

        menuService.dragAndDrop(request.getId(), request.getOldParent(), request.getNewParent(), request.getOldOrder(), request.getNewOrder());

        em.flush();
        em.clear();

        Menu parentMenu = menuRepository.findById(request.getNewParent())
                .orElseThrow(EntityNotFoundException::new);
        List<Menu> newMenus = parentMenu.getSubMenus().stream()
                .sorted(Comparator.comparingInt(Menu::getMenuOrder))
                .toList();

        assertThat(newMenus).hasSize(5)
                .extracting("name")
                .containsExactly("상의", "바지", "양말", "신발", "아우터");
    }

    private DragAndDropRequest setParentAndOrderMenuInit3() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 0);
        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);

        Integer menu2Id = createAndSaveMenu("아우터", menu1, 0);
        Integer menu3Id = createAndSaveMenu("상의", menu1, 1);
        Integer menu4Id = createAndSaveMenu("바지", menu1, 2);
        Integer menu5Id = createAndSaveMenu("양말", menu1, 3);
        Integer menu6Id = createAndSaveMenu("신발", menu1, 4);

        Menu menu2 = menuRepository.findById(menu2Id).orElse(null);

        return getSetParentAndOrderRequest(menu2Id, menu1Id, menu1Id, menu2.getMenuOrder(), 4);
    }

    @DisplayName("드래그앤 드롭으로 메뉴를 수정한다. 하위레벨에서 루트로 이동")
    @Transactional
    @Test
    public void setParentAndOrder4() {
        DragAndDropRequest request = setParentAndOrderMenuInit4();

        menuService.dragAndDrop(request.getId(), request.getOldParent(), request.getNewParent(), request.getOldOrder(), request.getNewOrder());

        em.flush();
        em.clear();

        List<Menu> newMenus = menuRepository.findAllByParentIsNullOrderByMenuOrder();

        assertThat(newMenus).hasSize(2)
                .extracting("name")
                .containsExactly("남성 의류", "여성 의류");
    }

    private DragAndDropRequest setParentAndOrderMenuInit4() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 0);
        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);

        Integer menu2Id = createAndSaveMenu("아우터", menu1, 0);
        Integer menu3Id = createAndSaveMenu("상의", menu1, 1);
        Integer menu4Id = createAndSaveMenu("바지", menu1, 2);
        Integer menu5Id = createAndSaveMenu("양말", menu1, 3);
        Integer menu6Id = createAndSaveMenu("신발", menu1, 4);
        Integer menu7Id = createAndSaveMenu("남성 의류", menu1, 5);

        Menu menu7 = menuRepository.findById(menu7Id).orElse(null);

        return getSetParentAndOrderRequest(menu7Id, menu1Id, null, menu7.getMenuOrder(), 0);
    }

    private DragAndDropRequest getSetParentAndOrderRequest(Integer menuId, Integer oldParentId, Integer newParentId, Integer oldOrder, Integer newOrder) {
        return DragAndDropRequest.builder()
                .id(menuId)
                .oldParent(oldParentId)
                .newParent(newParentId)
                .oldOrder(oldOrder)
                .newOrder(newOrder)
                .build();
    }

    private void menuInit() {
        // 최상위 메뉴 생성
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 2);
        Integer menu2Id = createAndSaveMenu("남성 의류", null, 1);
        Integer menu3Id = createAndSaveMenu("액세서리", null, 0);
        // 하위 메뉴 생성
        Menu menu1 = menuRepository.findById(menu1Id).orElse(null);
        Menu menu2 = menuRepository.findById(menu2Id).orElse(null);
        Menu menu3 = menuRepository.findById(menu3Id).orElse(null);

        Integer menu4Id = createAndSaveMenu("아우터", menu1, 1);
        Integer menu5Id = createAndSaveMenu("상의", menu1, 0);
        Integer menu6Id = createAndSaveMenu("아우터", menu2, 1);
        Integer menu7Id = createAndSaveMenu("바지", menu2, 0);
        Integer menu8Id = createAndSaveMenu("가방", menu3, 1);
        Integer menu9Id = createAndSaveMenu("신발", menu3, 0);

        // 하위 메뉴의 하위 메뉴 생성
        Menu menu4 = menuRepository.findById(menu4Id).orElse(null);
        Menu menu5 = menuRepository.findById(menu5Id).orElse(null);
        Menu menu6 = menuRepository.findById(menu6Id).orElse(null);
        Menu menu7 = menuRepository.findById(menu7Id).orElse(null);
        Menu menu8 = menuRepository.findById(menu8Id).orElse(null);
        Menu menu9 = menuRepository.findById(menu9Id).orElse(null);

        createAndSaveMenu("코트", menu4, 1);
        createAndSaveMenu("자켓", menu4, 0);
        createAndSaveMenu("블라우스", menu5, 1);
        createAndSaveMenu("코트", menu6, 0);
        createAndSaveMenu("패딩", menu6, 1);
        createAndSaveMenu("청바지", menu7, 0);
        createAndSaveMenu("크로스백", menu8, 0);
        createAndSaveMenu("스니커즈", menu9, 0);
        createAndSaveMenu("부츠", menu9, 2);
        createAndSaveMenu("샌들", menu9, 1);
        createAndSaveMenu("셔츠", menu5, 0);
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

    private MenuResponse getParentMenu(Set<MenuResponse> menus, String parentMenuName) {
        return menus.stream()
                .filter(menu -> menu.getText().equals(parentMenuName))
                .findFirst()
                .orElseThrow(AssertionError::new);
    }

    @Transactional
    @DisplayName("메뉴를 생성하면 최상위 메뉴 맨 마지막 순서로 생성된다.")
    @Test
    public void givenMenuForm_whenCreateMenu_thenSuccess() {
        createMenuInit();
        MenuForm form = MenuForm.builder()
                .name("가방")
                .icon("fa-solid fa-mitten")
                .build();

        Menu menu = menuService.create(form.toEntity());

        Menu findMenu = menuRepository.findById(menu.getId()).orElseThrow(EntityNotFoundException::new);

        assertThat(findMenu.getName()).isEqualTo("가방");
        assertThat(findMenu.getParent()).isNull();
        assertThat(findMenu.getIcon()).isEqualTo("fa-solid fa-mitten");
        assertThat(findMenu.getMenuOrder()).isEqualTo(3);
    }

    private void createMenuInit() {
        Integer menu1Id = createAndSaveMenu("여성 의류", null, 2);
        Integer menu2Id = createAndSaveMenu("남성 의류", null, 1);
        Integer menu3Id = createAndSaveMenu("액세서리", null, 0);
    }

}