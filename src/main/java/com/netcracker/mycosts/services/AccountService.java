package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.repositories.AccountRepository;
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

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account addAccount(Account account) {
        if(account != null) {
            accountRepository.save(account);
            return account;
        }
        return null;
    }

    public Account getById(int id) {
        if(id > 0) {
            return accountRepository.findById(id).get();
        }
        return null;
    }

    public void deleteById(int id) {
        if(id > 0) {
            accountRepository.deleteById(id);
        }
    }
}
