package com.netcracker.mycosts.repositories;

import java.util.List;
import com.netcracker.mycosts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAccountByUserId(String userId);
}