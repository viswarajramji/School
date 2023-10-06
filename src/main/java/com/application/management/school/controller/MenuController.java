package com.application.management.school.controller;

import com.application.management.school.dto.MenuObject;
import com.application.management.school.service.MenuService;
import com.application.management.school.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;
    private SessionService sessionService;

    @Autowired
    public MenuController(MenuService menuService,SessionService sessionService){
        this.menuService=menuService;
        this.sessionService=sessionService;
    }

    @PostMapping("/addMenu")
    public ResponseEntity<String> addMenu(@RequestBody MenuObject menuObject, HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
       return menuService.addMenu(menuObject);
    }

    @PutMapping("/updateMenu/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable(value = "menuId",required = true) String menuId,@RequestBody MenuObject menuObject, HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return menuService.updateMenu(Long.valueOf(menuId),menuObject);
    }

    @DeleteMapping("/deleteMenuById/{menuId}")
    public ResponseEntity<String> deleteMenuByMenuId(@PathVariable(name = "menuId",required = true) String menuId, HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return menuService.deleteMenuById(Long.valueOf(menuId));
    }

    @GetMapping("/getAllMenus")
    public ResponseEntity<List<MenuObject>> getAllMenus( HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return menuService.getAllMenus();
    }
}
