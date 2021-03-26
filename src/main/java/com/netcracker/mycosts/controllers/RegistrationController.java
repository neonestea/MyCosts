package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userService.getUserByEmail(user.getEmail());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        userService.create(user);
        return "redirect:/login";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}