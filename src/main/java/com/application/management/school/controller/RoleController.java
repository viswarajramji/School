package com.application.management.school.controller;

import com.application.management.school.dto.MenuObject;
import com.application.management.school.dto.RoleObject;
import com.application.management.school.service.RoleService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;
    private SessionService sessionService;

    @Autowired
    public RoleController(RoleService roleService,SessionService sessionService){
        this.roleService=roleService;
        this.sessionService=sessionService;
    }

    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestBody RoleObject roleObject,HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
       return roleService.addRole(roleObject);
    }

    @PutMapping("/updateRole/{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable(value = "roleId",required = true) String roleId,@RequestBody RoleObject roleObject,HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return roleService.updateRole(Long.valueOf(roleId),roleObject);
    }

    @DeleteMapping("/deleteRoleById/{roleId}")
    public ResponseEntity<String> deleteRoleByRoleId(@PathVariable(name = "roleId",required = true) String roleId,HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return roleService.deleteRoleById(Long.valueOf(roleId));
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<List<RoleObject>> getAllRoles(HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return roleService.getAllRoles();
    }

    @PostMapping("/addMenusToRole/{roleId}")
    public ResponseEntity<String> addMenusToRole(@PathVariable("roleId") String roleId, @RequestBody  List<String> menuIds,HttpSession session){
        menuIds=(menuIds!=null && !menuIds.isEmpty())?menuIds:new ArrayList<>();
        sessionService.checkIfLoggedInSuperUser(session);
        return roleService.addMenusToRole(Long.valueOf(roleId),menuIds);
    }

    @GetMapping("/getAllMenusByRoleId/{roleId}")
    public ResponseEntity<List<MenuObject>> getAllMenusByRoleId(@PathVariable("roleId") String roleId,HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return roleService.getAllMenusByRoleId(Long.valueOf(roleId));
    }
}
