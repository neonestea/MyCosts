package com.netcracker.mycosts.controllers;

import java.util.List;
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

@Controller
public class UserCategoryController {

    protected UserCategoryService userCategoryService;

    @Autowired
    public void setUserCategoryService(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    /*@GetMapping("/user/categories")
    List<UserCategory> all() {
        return userCategoryService.getAll();
    }

    @PostMapping("/user/categories")
    UserCategory newUserCategory(@RequestBody UserCategory newUserCategory) {
        return userCategoryService.addUserCategory(newUserCategory);
    }

    @DeleteMapping("/user/categories/{id}")
    void deleteUserCategory(@PathVariable int id) {
        userCategoryService.deleteById(id);
    }*/
}
