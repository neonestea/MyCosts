package com.netcracker.mycosts.services;

import java.util.List;
import java.util.Optional;


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

    /*public List<Cost> getAll(User user) {
        return costRepository.findBy();
    }

    public Cost addCost(Cost cost) {
        if(cost != null) {
            costRepository.save(cost);
            return cost;
        }
        return null;
    }

    public void deleteById(User user, int id) {
        if(id > 0) {
            costRepository.deleteById(id);
        }
    }*/
}
