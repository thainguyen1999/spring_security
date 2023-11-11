package com.example.spring_security.controller;

import com.example.spring_security.entity.User;
import com.example.spring_security.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDetailService userDetailService;
    @GetMapping("/welcome")
    public String welcome(ModelMap modelMap){
        modelMap.addAttribute("name","Hello user");
        return "welcome";
    }
    @GetMapping("register")
    public String showRegisterForm(ModelMap modelMap){
        modelMap.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            if (userDetailService.loadUserByUsername(user.getUserName())!=null){
                // kiểm tra xem người dùng đã tồn tại hay chưa
                return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        //luu nguoi dung moi
        userDetailService.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
