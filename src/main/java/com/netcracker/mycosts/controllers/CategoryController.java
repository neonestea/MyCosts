package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.entities.Category;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @PostMapping("/category")
    public void create(@RequestBody Category category, @AuthenticationPrincipal User user) {
        category = categoryService.save(category);
        if(!user.getCategories().contains(category)) {
            user.addCategory(category);
            userService.save(user);
        }
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

