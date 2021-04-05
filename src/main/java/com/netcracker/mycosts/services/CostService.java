package com.netcracker.mycosts.services;

import java.util.List;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.repositories.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostService {

    private CostRepository costRepository;

    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    public List<Cost> getAll(String userId) {
        return costRepository.findCostByUserId(userId);
    }

    public List<Cost> getAll(String userId, Account account) {
        return costRepository.findCostByUserIdAndAccount(userId, account);

    }

    public void save(Cost cost) {
         costRepository.save(cost);
    }
}
