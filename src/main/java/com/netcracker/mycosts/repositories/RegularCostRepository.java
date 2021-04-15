package com.netcracker.mycosts.repositories;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.RegularCost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegularCostRepository extends JpaRepository<RegularCost, Integer> {

    List<RegularCost> findRegularCostByUserId(String userId);
    void deleteRegularCostById(int id);
    List<RegularCost> findRegularCostByUserIdAndAccount(String userId, Account account);
}