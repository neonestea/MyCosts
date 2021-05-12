package com.netcracker.mycosts.controllers;

import java.util.List;

import com.netcracker.mycosts.dto.AccountDto;
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
    public ResponseEntity<AccountDto> create(@RequestBody @Valid AccountDto accountDto,
                                             @AuthenticationPrincipal User user) {
        Account account = accountDto.convertToAccount();
        List<Account> accountsFromDb = accountService.getAllUserAccounts(user.getId());
        for (Account acc : accountsFromDb) {
            if (acc.getActive() == false && acc.getName().equals(account.getName()) && acc.getCurrency() == account.getCurrency()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(AccountDto.convertFromAccount(acc));
            } else if (acc.getActive() == true && acc.getName().equals(account.getName()) && acc.getCurrency() == account.getCurrency()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }
        account.setUser(user);
        account.setActive(true);
        account = accountService.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(AccountDto.convertFromAccount(account));
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

    @PutMapping("/account")
    public ResponseEntity<Account> recover(@RequestBody Account account, @AuthenticationPrincipal User user) {
        Account accountToRecover = accountService.getAccountByUserAndAccountNameAndCurrencyName(user.getId(),
                account.getName(), account.getCurrency().name());
        accountToRecover.setActive(true);
        accountToRecover.setAmount(account.getAmount());
        return ResponseEntity.status(HttpStatus.OK).body(accountService.save(accountToRecover));
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
