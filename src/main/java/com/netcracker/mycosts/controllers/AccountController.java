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

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}/accounts")
    public List<Account> allAccounts(@PathVariable int userId) {
        return accountService.getAll(userId);
    }

    @PostMapping("/users/{userId}/accounts")
    public Account addAccount(@PathVariable int userId, @Valid @RequestBody Account account) {
        User user = userService.getUserById(userId);
        account.setUser(user);
        return accountService.create(account);
    }
}
