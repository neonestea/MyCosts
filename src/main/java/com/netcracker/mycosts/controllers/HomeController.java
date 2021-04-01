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

    @GetMapping("/categories")
    public String categories(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
            data.put("categories", user.getCategories());
            model.addAttribute("frontendData", data);
            return "categories";
        }
        else {
            return "redirect:/login";
        }
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
        }
        else {
            data.put("profile", null);
        }
        model.addAttribute("frontendData", data);
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
            model.addAttribute("frontendData", data);
            return "home";
        }
        else {
            return "redirect:/login";
        }
    }
    @GetMapping("/accounts")
    public String accounts(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if(user != null){
            data.put("profile", user);
            data.put("accounts", user.getAccounts());
            model.addAttribute("frontendData", data);
            return "accounts";
        }
        else {
            return "redirect:/login";
        }

    }

}
