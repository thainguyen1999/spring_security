package com.example.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/welcome")
    public  String welcome(ModelMap modelMap){
        modelMap.addAttribute("name","hello admin");

        return "welcome";
    }

}
