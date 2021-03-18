package com.netcracker.mycosts.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.entities.Account;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    protected AccountService accountService;


    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/accounts")
    List<Account> all() {
        return accountService.getAll();
    }

    @PostMapping("/accounts")
    Account newAccount(@RequestBody Account newAccount) {
        return accountService.addAccount(newAccount);
    }

    @GetMapping("/accounts/{id}")
    Account one(@PathVariable int id) {
        return accountService.getById(id);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable int id) {
        accountService.deleteById(id);
    }

}
