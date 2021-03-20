package com.netcracker.mycosts.services;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.repositories.AccountRepository;
import com.netcracker.mycosts.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    //все счета пользователя
    public List<Account> getAll(int userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    //добавление счета
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    //удаление всех счетов пользователя
    public void deleteAll(int userId){
        List<Account> accountsByUserId = accountRepository.findAccountByUserId(userId);
        for (int i = 0; i < accountsByUserId.size(); i++){
            accountRepository.deleteById(accountsByUserId.get(i).getId());
        }
    }

    //удаление одного счёта
    public void deleteOne(Account account){
        accountRepository.deleteById(account.getId());
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
