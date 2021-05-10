package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.PutMapping;


import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @PutMapping("/accept")
    public void update(@AuthenticationPrincipal User user) {
        User userFromDb = userService.getUserById(user.getId());
        userFromDb.setAccepted(true);
        userService.save(userFromDb);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public void addUser(@Valid @RequestBody User user) {
         userService.save(user);
    }

    @DeleteMapping("users/{id}/delete")
    public void deleteUser(@PathVariable String id) { userService.deleteById(id); }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
