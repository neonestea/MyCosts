package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Integer> {
    List<Cost> findCostByUserId(int userId);
    List<Cost> findCostByUserIdAndAccount(int userId, Account account);
}