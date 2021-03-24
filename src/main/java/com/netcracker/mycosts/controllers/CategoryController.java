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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/categories")
    public Set<Category> allUserCategories(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return user.getCategories();
    }

    @PostMapping("/users/{userId}/categories")
    public Category addUserCategory(@PathVariable int userId, @RequestParam String categoryName) {
        User user = userService.getUserById(userId);
        int nameHash = categoryName.toUpperCase().hashCode();
        Category category = categoryService.findCategoryByNameHash(nameHash);

        Set<User> categoryUsers;
        if (category == null) {
            category = Category.builder()
                    .name(categoryName)
                    .nameHash(nameHash)
                    .users(new HashSet<>())
                    .build();
        }

        category.addUser(user);

        return categoryService.findCategoryByNameHash(nameHash);
    }
}

