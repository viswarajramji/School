package com.application.management.school.service;

import com.application.management.school.Model.UserModel;
import com.application.management.school.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private UserRepository userRepository;

    public SessionService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ResponseEntity<String> login(Long userId, String password, HttpSession session) {
        Optional<UserModel> userModelOptional = this.userRepository.findById(userId);
        if(userModelOptional.isPresent()){
            UserModel userModel=userModelOptional.get();
            if(password.equals(userModel.getUserPassword())){
                session.setAttribute("userId", userId);
                session.setAttribute("superAdmin", false);
                if("SuperAdmin".equals(userModel.getRoleModel().getRoleName())){
                    session.setAttribute("superAdmin", true);
                }
                return ResponseEntity.ok().body("Login Succeeded");
            }
        }
        return ResponseEntity.ok().body("Invalid credentials");

    }

    public void checkAuthentication(HttpSession session){
        if(session.getAttribute("userId")==null){
            throw new RuntimeException("User Not Logged In");
        }
    }

    public void checkIfLoggedInSuperUser(HttpSession session){
        checkAuthentication(session);
        if(((Boolean)session.getAttribute("superAdmin"))==false){
            throw new RuntimeException("User is not super admin");
        }
    }



    public ResponseEntity<String> logout(HttpSession session) {
        session.setAttribute("userId", null);
        return ResponseEntity.ok().body("Logout Succeeded");
    }
}
