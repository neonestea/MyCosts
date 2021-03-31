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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @GetMapping("/category")
    public Set<Category> list(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        return user.getCategories();
    }

    @GetMapping("/categories")
    public String categories(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("categories", user.getCategories());

        model.addAttribute("frontendData", data);
        return "categories";
    }

    /*@GetMapping("/category")
    public Set<Category> allUserCategories(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return user.getCategories();
    }*/

    @PostMapping("/category")
    public Category create(@RequestBody Category category, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        category.addUser(user);
        return categoryService.save(category);
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

