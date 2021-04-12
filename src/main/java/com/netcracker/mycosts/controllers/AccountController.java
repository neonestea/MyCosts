package com.netcracker.mycosts.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.mycosts.services.AccountService;
import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    @PostMapping("/account")
    public ResponseEntity<Account> create(@RequestBody Account account, @AuthenticationPrincipal User user) {
        List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());
        for (Account acc : accountsFromDb) {
            if (acc.getActive() == false && acc.getName().equals(account.getName()) && acc.getCurrency() == account.getCurrency()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(acc);
            } else if (acc.getActive() == true && acc.getName().equals(account.getName()) && acc.getCurrency() == account.getCurrency()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }
        account.setUser(user);
        account.setActive(true);
        account = accountService.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        account.setActive(false);
        accountService.save(account);
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<Account> update(@PathVariable int id, @RequestBody(required=false) Account account, @AuthenticationPrincipal User user) {
        if (account != null) {
            List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());
            for (Account acc : accountsFromDb) {
                if (acc.getActive() == false && acc.getName().equals(account.getName())) {
                    return ResponseEntity.status(HttpStatus.CREATED).body(acc);
                } else if (acc.getActive() == true && acc.getName().equals(account.getName())) {
                    if (acc.getCurrency() == account.getCurrency() ){
                        Account accountFromDB = accountService.getAccountById(id);
                        accountFromDB.setAmount(account.getAmount());

                        return ResponseEntity.status(HttpStatus.OK).body(accountService.save(accountFromDB));

                    }
                    else {
                        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
                    }
                }

            }
            Account accountFromDB = accountService.getAccountById(id);
            accountFromDB.setName(account.getName());
            accountFromDB.setAmount(account.getAmount());
            return ResponseEntity.status(HttpStatus.OK).body(accountService.save(accountFromDB));
        }
        else {
            Account accountFromDB = accountService.getAccountById(id);
            accountFromDB.setActive(true);
            return ResponseEntity.status(HttpStatus.OK).body(accountService.save(accountFromDB));
        }
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
