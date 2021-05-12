package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public List<Account> getAllUserAccounts(String userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    public Account save(Account account) {
            return accountRepository.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).get();
    }

    public Account getAccountByUserAndAccountNameAndCurrencyName(String userId, String name, String currency) {
        return accountRepository.findAccountByUserIdAndNameAndCurrency(userId, name, currency);
    }
}
