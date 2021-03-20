package com.netcracker.mycosts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Cost;
import com.netcracker.mycosts.entities.User;
import com.netcracker.mycosts.repositories.CostRepository;
import org.hibernate.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.stereotype.Service;

@Service
public class CostService {

    protected CostRepository costRepository;


    @Autowired
    public void setCostRepository(CostRepository costRepository) {
        this.costRepository = costRepository;
    }

    //все траты пользователя
    public List<Cost> getAll(int userId) {
        return costRepository.findCostByUserId(userId);
    }

    //все траты пользователя по счёту
    public List<Cost> getAll(int userId, Account account) {
        return costRepository.findCostByUserIdAndAccount(userId, account);

    }

    //добавление траты
    public Cost create(Cost cost) {
        return costRepository.save(cost);
    }



}
