package com.netcracker.mycosts.controllers;

import java.util.HashSet;
import java.util.Set;

import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CategoryService;
import com.netcracker.mycosts.entities.Category;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @GetMapping("/users/{userId}/categories")
    public Set<Category> allUserCategories(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return user.getCategories();
    }

    @PostMapping("/users/{userId}/categories")
    public Category addUserCategory(@PathVariable int userId, @RequestParam String categoryName) {
        User user = userService.getUserById(userId);
        Category category = categoryService.findCategoryByName(categoryName);

        if (category == null || !categoryName.toLowerCase().equals(category.getName())) {
            category = Category.builder()
                    .name(categoryName)
                    .build();
        }

        category.addUser(user);
        categoryService.save(category);

        return category;
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

