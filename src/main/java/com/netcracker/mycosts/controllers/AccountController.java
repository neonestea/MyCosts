package com.netcracker.mycosts.controllers;

import java.util.List;
import java.util.Optional;

import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

@RestController
public class AccountController {

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected UserService userService;

    //получить счета пользователя
    @GetMapping("/users/{userId}/accounts")
    public List<Account> all(@PathVariable int userId) {
        return accountService.getAll(userId);
    }

    //добавить пользователю счёт
    @PostMapping("/users/{userId}/accounts/add")
    public Account add(@PathVariable int userId, @RequestBody Account account) {
        User user = userService.getUserById(userId);
        account.setUser(user);
        //user.setUserAccount(account);
        return account;
        //return accountService.create(account);
    }



    /*@GetMapping("/accounts/{id}")
    Account one(@PathVariable int id) {
        return accountService.getById(id);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable int id) {
        accountService.deleteById(id);
    }*/

}
