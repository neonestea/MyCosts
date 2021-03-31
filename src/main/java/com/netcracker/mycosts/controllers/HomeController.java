package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/home")
    public String home() {


        return "home";
    }
    @GetMapping("/accounts")
    public String accounts() {
        return "accounts";
    }



    @GetMapping("/userdetails")
    public String userDetails() {
        return "userdetails";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
