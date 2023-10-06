package com.application.management.school.service;

import com.application.management.school.Model.UserModel;
import com.application.management.school.dto.UserDetailsObject;
import com.application.management.school.dto.UserObject;
import com.application.management.school.Model.RoleModel;
import com.application.management.school.dto.child.MenuDetailsObject;
import com.application.management.school.dto.child.RoleDetailsObject;
import com.application.management.school.repository.RoleRepository;
import com.application.management.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,RoleRepository roleRepository){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
    }

    public ResponseEntity<String> addUser(UserObject userObject, String password){
        Optional<RoleModel> roleOptional=this.roleRepository.findById(userObject.getRoleId());
        if(!roleOptional.isPresent()){
             return ResponseEntity.badRequest().body("Invalid RoleId");
        }
        RoleModel role=roleOptional.get();
        UserModel user=new UserModel();
        user.setUserName(userObject.getUserName());
        user.setUserPassword(password);
        user.setRoleModel(role);
        this.userRepository.save(user);
        return ResponseEntity.ok().body("User has been created successfully");
    }

    public ResponseEntity<String> updateUser(Long userId, UserObject userObject, String password) {
        Optional<UserModel> savedUserModelOptional = this.userRepository.findById(userId);
        Optional<RoleModel> roleOptional=this.roleRepository.findById(userObject.getRoleId());
        if (!savedUserModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Id "+ String.valueOf(userId)+ " is Not Found");
        }
        if(!roleOptional.isPresent()){
            return ResponseEntity.badRequest().body("Invalid RoleId");
        }
        RoleModel role=roleOptional.get();
        UserModel userModel = savedUserModelOptional.get();
        userModel.setUserName(userObject.getUserName());
        userModel.setRoleModel(role);
        if(password!=null){
            userModel.setUserPassword(password);
        }
        this.userRepository.save(userModel);
        return ResponseEntity.ok().body("Update of user details was Successful");
    }

    public ResponseEntity<List<UserObject>> getAllUser() {
        List<UserModel> users=this.userRepository.findAll();
        List<UserObject> userObjectList=new ArrayList<>();
        users.forEach(user -> {
            UserObject userObject=new UserObject();
            userObject.setUserId(user.getUserId());
            userObject.setUserName(user.getUserName());
            userObject.setRoleId(user.getRoleModel().getRoleId());
            userObjectList.add(userObject);
        });
        return ResponseEntity.ok().body(userObjectList);
    }

    public ResponseEntity<UserDetailsObject> getUserDetailsByUserId(Long userId) {
        Optional<UserModel> userModelOptional = this.userRepository.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserDetailsObject());
        }
        UserModel userModel = userModelOptional.get();
        UserDetailsObject userDetailsObject=new UserDetailsObject();
        userDetailsObject.setUserId(userModel.getUserId());
        userDetailsObject.setUserName(userModel.getUserName());
        RoleModel roleModel=userModel.getRoleModel();
        RoleDetailsObject roleDetailsObject=new RoleDetailsObject();
        roleDetailsObject.setRoleId(roleModel.getRoleId());
        roleDetailsObject.setRoleName(roleModel.getRoleName());
        roleDetailsObject.setRoleDescription(roleModel.getRoleDescription());
        roleDetailsObject.setMenuDetailsObjectList(new ArrayList<>());
        roleModel.getMenuModelList().forEach(menuModel -> {
            MenuDetailsObject menuDetailsObject=new MenuDetailsObject();
            menuDetailsObject.setMenuId(menuModel.getMenuId());
            menuDetailsObject.setMenuName(menuModel.getMenuName());
            menuDetailsObject.setMenuDescription(menuModel.getMenuDescription());
            menuDetailsObject.setMenuLink(menuModel.getMenuLink());
            roleDetailsObject.getMenuDetailsObjectList().add(menuDetailsObject);
        });
        userDetailsObject.setRoleDetailsObject(roleDetailsObject);
        return ResponseEntity.ok().body(userDetailsObject);
    }

    public ResponseEntity<UserDetailsObject> createDefaultSuperUserAndSuperAdminRole() {
        UserModel userModel =new UserModel();
        userModel.setUserName("SuperAdmin");
        userModel.setUserPassword("SuperAdmin");

        RoleModel roleModel=new RoleModel();
        roleModel.setRoleName("SuperAdmin");
        roleModel.setRoleDescription("Super admin role with this users can actually use the application");
        roleModel.setUserModelList(List.of(userModel));

        userModel.setRoleModel(roleModel);
        this.roleRepository.save(roleModel);
        this.userRepository.save(userModel);

        return getUserDetailsByUserId(userModel.getUserId());
    }
}
