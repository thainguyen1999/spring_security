package com.example.spring_security.controller;

import com.example.spring_security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class AuthenController {
    @Autowired
     AuthenticationManager authenticationManager;


    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestBody User user){
        try{
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return "index";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

}
