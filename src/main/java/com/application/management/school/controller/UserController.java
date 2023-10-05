package com.application.management.school.controller;

import com.application.management.school.dto.UserDetailsObject;
import com.application.management.school.dto.UserObject;
import com.application.management.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserObject userObject, @RequestHeader Map<String, String> headers){
       return userService.addUser(userObject,headers.get("password"));
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "userId",required = true) String userId, @RequestBody UserObject userObject, @RequestHeader Map<String, String> headers){
        if(headers.get("password")==null){
            return userService.updateUser(Long.valueOf(userId),userObject,null);
        }
        return userService.updateUser(Long.valueOf(userId),userObject,headers.get("password"));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserObject>> getAllUsers(){
        return userService.getAllUser();
    }

    @GetMapping("/getUserDetailsByUserId/{userId}")
    public ResponseEntity<UserDetailsObject> getUserDetailsByUserId(@PathVariable(value = "userId",required = true) String userId){
        return userService.getUserDetailsByUserId(Long.valueOf(userId));
    }
}
