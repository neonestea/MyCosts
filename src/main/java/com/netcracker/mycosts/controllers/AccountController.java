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
        //TODO проверить обновление базы
        //account = accountService.save(account);
        if(!user.getAccounts().contains(account)) {
            user.addAccount(account);
            account.setUser(user);
            account.setActive(true);
            account = accountService.save(account);
            userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(account);

        }
        else {
            Set<Account> accountsFromDb = user.getAccounts();
            System.out.println("CHECKING");
            for (Account acc : accountsFromDb){
                System.out.println(acc.getName().toUpperCase(Locale.ROOT));
                System.out.println("NOT ACTIVE " + (acc.getActive() == false));
                System.out.println("NAMES " + (acc.getName().equals(account.getName())));
                System.out.println("CURRENCIES " + (acc.getCurrency() == account.getCurrency()));
                if (acc.getActive() == false && acc.getName().equals(account.getName()) && acc.getCurrency() == account.getCurrency()){
                    System.out.println("RECOVER");
                    return ResponseEntity.status(HttpStatus.CREATED).body(acc);
                }
            }
            System.out.println("EXISTING");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable int id) {
        Account account = accountService.getAccountById(id);
        account.setActive(false);
        accountService.save(account);
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<Account> update(@PathVariable int id, @RequestBody Account account, @AuthenticationPrincipal User user) {
        Account accountFromDB = accountService.getAccountById(id);
        accountFromDB.setName(account.getName());
        accountFromDB.setAmount(account.getAmount());
        //TODO проверить обновление базы
        /*Set<Account> accountsFromDb = user.getAccounts();
        for (Account acc : accountsFromDb){
            if (acc.getActive() == false && accountFromDB.getName().equals(account.getName()) && accountFromDB.getCurrency() == account.getCurrency()){
                //ask to recover account
                return ResponseEntity.status(HttpStatus.CREATED).body(acc);
            }
            else if(acc.getActive() == true && accountFromDB.getName().equals(account.getName()) && accountFromDB.getCurrency() == account.getCurrency()){
                //notify that user already has active account
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }*/

        return ResponseEntity.status(HttpStatus.OK).body(accountService.save(accountFromDB));

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
