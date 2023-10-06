package com.application.management.school.service;

import com.application.management.school.dto.MenuObject;
import com.application.management.school.Model.MenuModel;
import com.application.management.school.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {


    private MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository){
        this.menuRepository=menuRepository;
    }

    public ResponseEntity<String> addMenu(MenuObject menuObject){
        MenuModel menu=new MenuModel();
        menu.setMenuName(menuObject.getMenuName());
        menu.setMenuLink(menuObject.getMenuLink());
        menu.setMenuDescription(menuObject.getMenuDescription());
        this.menuRepository.save(menu);
        return ResponseEntity.ok().body("Menu is saved Successfully");
    }

    public ResponseEntity<String> updateMenu(Long menuId,MenuObject menuObject) {
        Optional<MenuModel> savedMenu = this.menuRepository.findById(menuId);
        if (savedMenu.isPresent()) {
            MenuModel menu = savedMenu.get();
            menu.setMenuName(menuObject.getMenuName());
            menu.setMenuLink(menuObject.getMenuLink());
            menu.setMenuDescription(menuObject.getMenuDescription());
            this.menuRepository.save(menu);
            return ResponseEntity.ok().body("Menu has been updated Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu with Id" +String.valueOf(menuId)+" is Not Found");
    }

    public ResponseEntity<String> deleteMenuById(Long menuId) {
        Optional<MenuModel> savedMenu = this.menuRepository.findById(menuId);
        if (savedMenu.isPresent()) {
            this.menuRepository.delete(savedMenu.get());
            return ResponseEntity.ok().body("Menu was deleted Successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu with Id" +String.valueOf(menuId)+" is Not Found");
    }

    public ResponseEntity<List<MenuObject>> getAllMenus() {
        List<MenuModel> menus = this.menuRepository.findAll();
        List<MenuObject> menuObjectList=new ArrayList<>();
        menus.forEach(menu -> {
            MenuObject menuObject=new MenuObject();
            menuObject.setMenuId(menu.getMenuId());
            menuObject.setMenuName(menu.getMenuName());
            menuObject.setMenuLink(menu.getMenuLink());
            menuObject.setMenuDescription(menu.getMenuDescription());
            menuObjectList.add(menuObject);
        });
        return ResponseEntity.ok().body(menuObjectList);
    }




}
