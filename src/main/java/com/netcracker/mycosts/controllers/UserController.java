package com.netcracker.mycosts.controllers;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.repositories.UserRepository;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.entities.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class UserController {
    @Autowired
    protected UserService userService;

    //получить всех пользователей
    @GetMapping("/users")
    public List<User> all() {
        return userService.findAll();
    }

    //получить пользователя по id
    @GetMapping("/users/{id}")
    public User getOne(@PathVariable int id) {
        return userService.getUserById(id);
    }

    //добавление пользователя
    @PostMapping("/users/add")
    public User add(@RequestBody User user) {
        return userService.create(user);
    }

    //удаление пользователя
    @DeleteMapping("users/{id}/delete")
    public void delete(@PathVariable int id) { userService.deleteById(id); }

}
