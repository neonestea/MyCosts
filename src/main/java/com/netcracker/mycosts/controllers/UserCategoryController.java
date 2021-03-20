package com.netcracker.mycosts.controllers;

import java.util.List;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.UserCategoryService;
import com.netcracker.mycosts.entities.UserCategory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
public class UserCategoryController {
    @Autowired
    private UserCategoryService userCategoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/categories")
    public List<UserCategory> allUserCategories(@PathVariable int userId) {
        return userCategoryService.getAll(userId);
    }

    @PostMapping("/users/{userId}/categories")
    public UserCategory addUserCategory(@PathVariable int userId, @Valid @RequestBody UserCategory userCategory) {
        User user = userService.getUserById(userId);
        userCategory.setUser(user);
        return userCategoryService.create(userCategory);
    }
}
