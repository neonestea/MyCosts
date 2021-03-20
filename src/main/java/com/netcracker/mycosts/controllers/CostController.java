package com.netcracker.mycosts.controllers;

import java.util.List;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.CostService;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@RestController
public class CostController {

    protected CostService costService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AccountService accountService;

    @Autowired
    public void setCostService(CostService costService) {
        this.costService = costService;
    }

    @GetMapping("/users/{userId}/costs")
    public List<Cost> all(@PathVariable int userId) {
        return costService.getAll(userId);
    }

    @PostMapping("/users/{userId}/accounts/{accountId}/costs")
    public Cost addCost(@PathVariable int userId, @PathVariable int accountId, @Valid @RequestBody Cost cost) {
        User user = userService.getUserById(userId);
        Account account = accountService.getAccountById(accountId);
        cost.setUser(user);
        cost.setAccount(account);
        System.out.println("COST " + cost.toString());
        return costService.create(cost);
    }


}
