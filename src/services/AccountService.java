package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.entities.UserCategory;
import com.netcracker.mycosts.repositories.AccountRepository;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    protected AccountRepository accountRepository;
    protected UserRepository userRepository;


    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll(int userId) {
        return accountRepository.findAll();
    }

    public Optional<Account> addAccount(String name, int userId, float amount) {
        if (name.length() != 0 && userId >= 1){
            if (amount != 0){
                Account account = Account.builder()
                        .name(name)
                        .id(userId)
                        .amount(amount)
                        .build();
                accountRepository.save(account);
                return Optional.of(account);
            }
            else {
                Account account = Account.builder()
                        .name(name)
                        .id(userId)
                        .amount(0)
                        .build();
                accountRepository.save(account);
                return Optional.of(account);
            }
        }
    else return null;
    }

    public void deleteById(int id) {
        if(id > 0) {
            accountRepository.deleteById(id);
        }
    }
}
