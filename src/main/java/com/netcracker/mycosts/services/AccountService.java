package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.AccountRepository;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AccountService {

    protected AccountRepository accountRepository;


    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll(int userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).get();
    }
}
