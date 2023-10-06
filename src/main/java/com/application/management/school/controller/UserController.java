package com.application.management.school.controller;

import com.application.management.school.dto.UserDetailsObject;
import com.application.management.school.dto.UserObject;
import com.application.management.school.service.SessionService;
import com.application.management.school.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    private SessionService sessionService;

    @Autowired
    public UserController(UserService userService,SessionService sessionService){
        this.userService=userService;
        this.sessionService=sessionService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserObject userObject, @RequestHeader Map<String, String> headers){
        return userService.addUser(userObject,headers.get("password"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestHeader Map<String, String> headers, HttpSession session){
        String userId=  headers.get("userid");
        String password=headers.get("password");
        return sessionService.login(Long.valueOf(userId),password,session);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        return sessionService.logout(session);
    }

    @GetMapping("/getUserDetailsByUserId/{userId}")
    public ResponseEntity<UserDetailsObject> getUserDetailsByUserId(@PathVariable(value = "userId",required = true) String userId,HttpSession session){
        sessionService.checkAuthentication(session);
        return userService.getUserDetailsByUserId(Long.valueOf(userId));
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "userId",required = true) String userId, @RequestBody UserObject userObject, @RequestHeader Map<String, String> headers,HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        if(headers.get("password")==null){
            return userService.updateUser(Long.valueOf(userId),userObject,null);
        }
        return userService.updateUser(Long.valueOf(userId),userObject,headers.get("password"));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserObject>> getAllUsers(HttpSession session){
        sessionService.checkIfLoggedInSuperUser(session);
        return userService.getAllUser();
    }



}
