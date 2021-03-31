package com.netcracker.mycosts.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.entities.Category;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

/*    @GetMapping("/category")
    public Set<Category> list(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return user.getCategories();
    }*/



    @PostMapping("/category")
    public void create(@RequestBody Category category, @AuthenticationPrincipal User user) {
        //category.addUser(user);
        user.addCategory(category);
        System.out.println(category);
        categoryService.save(category);
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

