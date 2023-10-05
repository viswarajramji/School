package com.application.management.school.service;


import com.application.management.school.Model.MenuModel;
import com.application.management.school.Model.RoleModel;
import com.application.management.school.dto.MenuObject;
import com.application.management.school.dto.RoleObject;
import com.application.management.school.repository.MenuRepository;
import com.application.management.school.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RoleService {

    private RoleRepository roleRepository;
    private MenuRepository menuRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, MenuRepository menuRepository){
        this.roleRepository=roleRepository;
        this.menuRepository=menuRepository;
    }

    public ResponseEntity<String> addRole(RoleObject roleObject){
        RoleModel roleEntity=new RoleModel();
        roleEntity.setRoleName(roleObject.getRoleName());
        roleEntity.setRoleDescription(roleObject.getRoleDescription());
        this.roleRepository.save(roleEntity);
        return ResponseEntity.ok().body("Role has been created successfully");
    }

    public ResponseEntity<String> updateRole(Long roleId,RoleObject roleObject) {
        Optional<RoleModel> savedRole = this.roleRepository.findById(roleId);
        if (savedRole.isPresent()) {
            RoleModel role = savedRole.get();
            role.setRoleName(roleObject.getRoleName());
            role.setRoleDescription(roleObject.getRoleDescription());
            this.roleRepository.save(role);
            return ResponseEntity.ok().body("Update of role was Successful");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role Id "+ String.valueOf(roleId)+ " is Not Found");
    }

    public ResponseEntity<String> deleteRoleById(Long roleId) {
        Optional<RoleModel> savedRole = this.roleRepository.findById(roleId);
        if (savedRole.isPresent()) {
            this.roleRepository.deleteById(roleId);
            return ResponseEntity.ok().body("Delete of role was Success");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role Id "+ String.valueOf(roleId)+ " is Not Found");
    }


    public ResponseEntity<List<RoleObject>> getAllRoles() {
      List<RoleModel> roles = this.roleRepository.findAll();
      List<RoleObject> roleObjectList=new ArrayList<>();
      roles.forEach(role -> {
          RoleObject roleObject=new RoleObject();
          roleObject.setRoleId(role.getRoleId());
          roleObject.setRoleName(role.getRoleName());
          roleObject.setRoleDescription(role.getRoleDescription());
          roleObjectList.add(roleObject);
      });
      return ResponseEntity.ok().body(roleObjectList);
    }


    public ResponseEntity<String> addMenusToRole(Long roleId, List<String> menuIds) {
        List<Long> menuIdsLong = menuIds.stream().map(Long::parseLong).collect(Collectors.toList());
        Optional<RoleModel> roleOptional = this.roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role Id Not found");
        }
        RoleModel role=roleOptional.get();
        List<MenuModel> menuModelList = menuRepository.findAllById(menuIdsLong);
        if(menuModelList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Menu Id list");
        }
        menuModelList.forEach(menuModel -> {
            menuModel.getRoleModelList().add(role);
        });
        role.setMenuModelList(menuModelList);
        this.roleRepository.save(role);
        this.menuRepository.saveAll(menuModelList);
        return ResponseEntity.ok().body("Roles and Menu are Saved Successfully");
    }

    public ResponseEntity<List<MenuObject>> getAllMenusByRoleId(Long roleId) {
        Optional<RoleModel> savedRole = this.roleRepository.findById(roleId);
        List<MenuObject> menuModelList=new ArrayList<>();
        if(!savedRole.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        RoleModel roleModel=savedRole.get();
        roleModel.getMenuModelList().forEach(menuModel -> {
            MenuObject menuObject=new MenuObject();
            menuObject.setMenuId(menuModel.getMenuId());
            menuObject.setMenuName(menuModel.getMenuName());
            menuObject.setMenuLink(menuModel.getMenuLink());
            menuObject.setMenuDescription(menuModel.getMenuDescription());
            menuModelList.add(menuObject);
        });
        return ResponseEntity.ok().body(menuModelList);
    }
}
