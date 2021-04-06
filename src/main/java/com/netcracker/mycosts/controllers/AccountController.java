package com.netcracker.mycosts.controllers;

import java.util.List;
import java.util.Set;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    /*@GetMapping("/users/{userId}/accounts")
    public List<Account> allAccounts(@PathVariable int userId) {
        return accountService.getAll(userId);
    }*/

    @PostMapping("/account")
    public Account create(@RequestBody Account account, @AuthenticationPrincipal User user) {
        Set<Account> accountsFromDb = user.getAccounts();
        //TODO проверить, что такого аккаунта еще не было
        account = accountService.save(account);
        if(!user.getAccounts().contains(account)) {
            user.addAccount(account);
            account.setUser(user);
            account.setActive(true);
            account = accountService.save(account);
            userService.save(user);
        }
        return account;
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        account.setActive(false);
        accountService.save(account);
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
