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

@RestController
public class UserCategoryController {

    protected UserCategoryService userCategoryService;

    @Autowired
    public void setUserCategoryService(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("/costs")
    List<UserCategory> all() {
        return userCategoryService.getAll();
    }

    @PostMapping("/costs")
    UserCategory newUserCategory(@RequestBody UserCategory newUserCategory) {
        return userCategoryService.addUserCategory(newUserCategory);
    }

    @GetMapping("/costs/{id}")
    UserCategory one(@PathVariable int id) {
        return userCategoryService.getById(id);
    }

    @DeleteMapping("/costs/{id}")
    void deleteUserCategory(@PathVariable int id) {
        userCategoryService.deleteById(id);
    }
}
